package com.nguyenhien.lotus_reward.modules.user.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileCreateRequest;
import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileResponse;
import com.nguyenhien.lotus_reward.modules.user.entities.User;
import com.nguyenhien.lotus_reward.modules.user.mappers.IUserMapper;
import com.nguyenhien.lotus_reward.modules.user.repositories.IUserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserJpaRepository userJpaRepository;
    private final IUserMapper userMapper;

    @Override
    @Transactional
    public UserProfileResponse createUser(UserProfileCreateRequest request) {
        if (request == null) {
            return null;
        }
        // Check user is existed by username
        if (userJpaRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("User is existed");
        }
        User user = userJpaRepository.save(userMapper.toEntity(request));
        return userMapper.toResponse(user);
    }
}
