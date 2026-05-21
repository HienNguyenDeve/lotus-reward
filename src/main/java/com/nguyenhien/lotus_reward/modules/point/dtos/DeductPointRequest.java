package com.nguyenhien.lotus_reward.modules.point.dtos;

import java.util.UUID;

import com.nguyenhien.lotus_reward.modules.point.enums.ReferenceType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeductPointRequest {
    @NotNull
    private UUID userId;

    @NotNull
    @Min(1)
    private Long point;

    @NotNull
    private ReferenceType referenceType;

    private String description;
}
