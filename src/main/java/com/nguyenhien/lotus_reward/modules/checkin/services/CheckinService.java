package com.nguyenhien.lotus_reward.modules.checkin.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinStatusItemResponse;
import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinStatusResponse;
import com.nguyenhien.lotus_reward.modules.checkin.entities.CheckinRecord;
import com.nguyenhien.lotus_reward.modules.checkin.repositories.ICheckinJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckinService implements ICheckinService {
    private static final List<Integer> REWARDS = List.of(1, 2, 3, 5, 8, 13, 21);

    private final ICheckinJpaRepository checkinRecordRepository;

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
}
