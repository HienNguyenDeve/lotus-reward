package com.nguyenhien.lotus_reward.modules.point.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenhien.lotus_reward.modules.point.dtos.CustomResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionSearchRequest;
import com.nguyenhien.lotus_reward.modules.point.services.IPointTransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pointTransactions")
@RequiredArgsConstructor
public class PointTransactionController {
    private final IPointTransactionService pointTransactionService;

    private final PagedResourcesAssembler<PointTransactionResponse> pagedResourcesAssembler; 

    //Search pagination
    @GetMapping("/pagination")
    public ResponseEntity<CustomResponse<PointTransactionResponse>> search(@ModelAttribute PointTransactionSearchRequest request) {
        Page<PointTransactionResponse> result = pointTransactionService.pagination(request);
                // Convert to paged model
        var pagedModel = pagedResourcesAssembler.toModel(result);
        // Prepare response
        var response = new CustomResponse<PointTransactionResponse>(
                pagedModel.getContent(),
                pagedModel.getMetadata(),
                pagedModel.getLinks()
            );
        return ResponseEntity.ok(response);
    }
}
