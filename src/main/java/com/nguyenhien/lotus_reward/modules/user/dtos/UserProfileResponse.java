package com.nguyenhien.lotus_reward.modules.user.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private UUID id;

    private String username;

    private String fullName;

    private String avatarUrl;

    private Long lotusPoint;
}
