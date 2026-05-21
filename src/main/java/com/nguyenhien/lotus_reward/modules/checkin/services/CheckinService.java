package com.nguyenhien.lotus_reward.modules.checkin.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinResponse;
import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinStatusItemResponse;
import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinStatusResponse;
import com.nguyenhien.lotus_reward.modules.checkin.entities.CheckinRecord;
import com.nguyenhien.lotus_reward.modules.checkin.repositories.ICheckinJpaRepository;
import com.nguyenhien.lotus_reward.modules.point.entities.PointTransaction;
import com.nguyenhien.lotus_reward.modules.point.enums.PointTransactionType;
import com.nguyenhien.lotus_reward.modules.point.enums.ReferenceType;
import com.nguyenhien.lotus_reward.modules.point.repositories.IPointTransactionJpaRepository;
import com.nguyenhien.lotus_reward.modules.user.entities.User;
import com.nguyenhien.lotus_reward.modules.user.repositories.IUserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckinService implements ICheckinService {
        private static final List<Integer> REWARDS = List.of(1, 2, 3, 5, 8, 13, 21);

        private final ICheckinJpaRepository checkinRecordRepository;

        private final IUserJpaRepository userJpaRepository;

        private final IPointTransactionJpaRepository pointTransactionJpaRepository;

        private final RedissonClient redissonClient;

        private final StringRedisTemplate redisTemplate;

        @Override
        public CheckinStatusResponse getCheckinStatus(UUID userId) {

                YearMonth currentMonth = YearMonth.now();

                LocalDate startDate = currentMonth.atDay(1);

                LocalDate endDate = currentMonth.atEndOfMonth();

                List<CheckinRecord> records = checkinRecordRepository
                                .findByUserIdAndCheckinDateBetween(
                                                userId,
                                                startDate,
                                                endDate);

                List<CheckinStatusItemResponse> items = new ArrayList<>();

                for (int i = 0; i < REWARDS.size(); i++) {

                        int order = i + 1;

                        CheckinRecord matchedRecord = records.stream()
                                        .filter(record -> record.getCheckinOrderInMonth() == order)
                                        .findFirst()
                                        .orElse(null);

                        items.add(
                                        CheckinStatusItemResponse.builder()
                                                        .order(order)
                                                        .rewardPoint(REWARDS.get(i))
                                                        .checked(matchedRecord != null)
                                                        .checkinDate(
                                                                        matchedRecord != null
                                                                                        ? matchedRecord.getCheckinDate()
                                                                                        : null)
                                                        .build());
                }

                return CheckinStatusResponse.builder()
                                .month(currentMonth.toString())
                                .totalCheckedIn(records.size())
                                .maxCheckin(REWARDS.size())
                                .items(items)
                                .build();
        }

        @Override
        @Transactional
        public CheckinResponse checkin(UUID userId) {
                String lockKey = "lock:checkin:user:" + userId;

                RLock lock = redissonClient.getLock(lockKey);

                try {

                        boolean locked = lock.tryLock(3, 10, TimeUnit.SECONDS);

                        if (!locked) {
                                throw new RuntimeException(
                                                "System busy, please try again");
                        }

                        LocalDate today = LocalDate.now();

                        String redisCheckinKey = "checkin:user:" + userId + ":" + today;

                        // fast check bằng redis
                        Boolean exists = redisTemplate.hasKey(redisCheckinKey);

                        if (Boolean.TRUE.equals(exists)) {
                                throw new RuntimeException(
                                                "Already checked in today");
                        }

                        validateCheckinTime();

                        boolean checkedIn = checkinRecordRepository
                                        .existsByUserIdAndCheckinDate(
                                                        userId,
                                                        today);

                        if (checkedIn) {
                                throw new RuntimeException(
                                                "Already checked in today");
                        }

                        User user = userJpaRepository.findById(userId)
                                        .orElseThrow(() -> new RuntimeException("User not found"));

                        YearMonth currentMonth = YearMonth.now();

                        long monthlyCount = checkinRecordRepository
                                        .countByUserIdAndCheckinDateBetween(
                                                        userId,
                                                        currentMonth.atDay(1),
                                                        currentMonth.atEndOfMonth());

                        if (monthlyCount >= 7) {
                                throw new RuntimeException(
                                                "Monthly checkin limit exceeded");
                        }

                        int reward = REWARDS.get((int) monthlyCount);

                        long balanceBefore = user.getLotusPoint();

                        long balanceAfter = balanceBefore + reward;

                        user.setLotusPoint(balanceAfter);

                        int checkinOrder = (int) monthlyCount + 1;

                        CheckinRecord checkinRecord = new CheckinRecord();

                        checkinRecord.setUser(user);
                        checkinRecord.setCheckinDate(today);
                        checkinRecord.setRewardPoint(reward);
                        checkinRecord.setCheckinOrderInMonth(
                                        checkinOrder);

                        checkinRecordRepository.save(checkinRecord);

                        PointTransaction transaction = new PointTransaction();

                        transaction.setUser(user);

                        transaction.setType(
                                        PointTransactionType.EARN);

                        transaction.setPoint((long) reward);

                        transaction.setBalanceBefore(
                                        balanceBefore);

                        transaction.setBalanceAfter(
                                        balanceAfter);

                        transaction.setReferenceType(
                                        ReferenceType.CHECKIN);

                        transaction.setReferenceId(
                                        checkinRecord.getId());

                        pointTransactionJpaRepository.save(
                                        transaction);

                        userJpaRepository.save(user);

                        // đánh dấu redis
                        redisTemplate.opsForValue().set(
                                        redisCheckinKey,
                                        "1",
                                        Duration.ofDays(1));

                        return CheckinResponse.builder()
                                        .rewardPoint(reward)
                                        .totalPoint(balanceAfter)
                                        .checkinOrder(checkinOrder)
                                        .checkinDate(today)
                                        .build();

                } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();

                        throw new RuntimeException(
                                        "Checkin interrupted");

                } finally {

                        if (lock.isHeldByCurrentThread()) {
                                lock.unlock();
                        }
                }

        }

        private void validateCheckinTime() {

                LocalTime now = LocalTime.now();

                boolean morning = now.isAfter(LocalTime.of(9, 0))
                                && now.isBefore(LocalTime.of(11, 0));

                boolean evening = now.isAfter(LocalTime.of(19, 0))
                                && now.isBefore(LocalTime.of(21, 0));

                if (!(morning || evening)) {

                        throw new RuntimeException(
                                        "Not in checkin time window");
                }
        }
}
