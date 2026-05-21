package com.nguyenhien.lotus_reward.modules.point.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenhien.lotus_reward.modules.point.entities.PointTransaction;

public interface IPointTransactionJpaRepository extends JpaRepository<PointTransaction, UUID>{

}
