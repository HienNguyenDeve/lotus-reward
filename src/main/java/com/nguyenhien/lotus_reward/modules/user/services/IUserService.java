package com.nguyenhien.lotus_reward.modules.user.services;

import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileCreateRequest;
import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileResponse;

public interface IUserService {
    UserProfileResponse createUser (UserProfileCreateRequest request);
}
