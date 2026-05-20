package com.nguyenhien.lotus_reward.modules.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileCreateRequest;
import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileResponse;
import com.nguyenhien.lotus_reward.modules.user.services.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping()
    public ResponseEntity<UserProfileResponse> createUserProfile (@RequestBody @Valid UserProfileCreateRequest request) {
        UserProfileResponse result = userService.createUser(request);
        return ResponseEntity.ok().body(result);
    }
}
