package com.nguyenhien.lotus_reward.modules.checkin.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckinStatusItemResponse {
    private Integer order;

    private Integer rewardPoint;

    private Boolean checked;

    private LocalDate checkinDate;
}
