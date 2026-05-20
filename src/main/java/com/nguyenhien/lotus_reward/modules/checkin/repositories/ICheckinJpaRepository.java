package com.nguyenhien.lotus_reward.modules.checkin.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenhien.lotus_reward.modules.checkin.entities.CheckinRecord;

public interface ICheckinJpaRepository extends JpaRepository<CheckinRecord, UUID> {
    List<CheckinRecord> findByUserIdAndCheckinDateBetween(
            UUID userId,
            LocalDate startDate,
            LocalDate endDate);
}
