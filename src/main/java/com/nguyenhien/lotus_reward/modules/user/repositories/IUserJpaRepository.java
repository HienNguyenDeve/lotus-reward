package com.nguyenhien.lotus_reward.modules.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nguyenhien.lotus_reward.modules.user.entities.User;

public interface IUserJpaRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User>{
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
