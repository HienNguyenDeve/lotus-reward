package com.nguyenhien.lotus_reward.modules.checkin.dtos;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckinResponse {
    private Integer rewardPoint;

    private Long totalPoint;

    private Integer checkinOrder;

    private LocalDate checkinDate;
}
