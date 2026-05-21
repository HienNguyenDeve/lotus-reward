package com.nguyenhien.lotus_reward.modules.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.nguyenhien.lotus_reward.modules.user.entities.User;

import jakarta.persistence.LockModeType;

public interface IUserJpaRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                select u
                from User u
                where u.id = :id
            """)
    Optional<User> findByIdForUpdate(UUID id);
}
