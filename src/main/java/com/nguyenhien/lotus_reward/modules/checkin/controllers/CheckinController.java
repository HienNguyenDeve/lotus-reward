package com.nguyenhien.lotus_reward.modules.checkin.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinRequest;
import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinResponse;
import com.nguyenhien.lotus_reward.modules.checkin.dtos.CheckinStatusResponse;
import com.nguyenhien.lotus_reward.modules.checkin.services.ICheckinService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/checkins")
@RequiredArgsConstructor
public class CheckinController {
    private final ICheckinService checkinService;

    @GetMapping("/status")
    public ResponseEntity<CheckinStatusResponse> getCheckinStatus(@RequestParam UUID userId) {
        CheckinStatusResponse result = checkinService.getCheckinStatus(userId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping()
    public ResponseEntity<CheckinResponse> checkin(@RequestBody CheckinRequest request) {
        CheckinResponse result = checkinService.checkin(request.getUserId());
        return ResponseEntity.ok().body(result);
    }
}
