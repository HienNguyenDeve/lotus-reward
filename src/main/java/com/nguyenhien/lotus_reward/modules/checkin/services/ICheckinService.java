package com.nguyenhien.lotus_reward.modules.checkin.services;

import java.util.UUID;

import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinResponse;
import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinStatusResponse;

public interface ICheckinService {
    CheckinStatusResponse getCheckinStatus(UUID userId);
    CheckinResponse checkin(UUID userId);
}
