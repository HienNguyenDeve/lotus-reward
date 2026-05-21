package com.nguyenhien.lotus_reward.modules.point.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeductPointResponse {

    private Long deductedPoint;

    private Long remainingPoint;
}
