package com.nguyenhien.lotus_reward.modules.user.services;

import java.util.UUID;

import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileCreateRequest;
import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileResponse;

public interface IUserService {
    UserProfileResponse createUser (UserProfileCreateRequest request);
    UserProfileResponse findById(UUID id);
}
