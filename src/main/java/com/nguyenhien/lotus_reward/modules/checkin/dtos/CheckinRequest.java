package com.nguyenhien.lotus_reward.modules.checkin.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckinRequest {
    private UUID userId;
}
