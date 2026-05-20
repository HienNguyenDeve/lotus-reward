package com.nguyenhien.lotus_reward.modules.checkin.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckinStatusResponse {
    private String month;

    private Integer totalCheckedIn;

    private Integer maxCheckin;

    private List<CheckinStatusItemResponse> items;
}
