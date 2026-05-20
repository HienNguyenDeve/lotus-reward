package com.nguyenhien.lotus_reward.modules.user.dtos;

import jakarta.validation.constraints.NotBlank;
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
public class UserProfileCreateRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String fullName;

    private String avatarUrl;
}
