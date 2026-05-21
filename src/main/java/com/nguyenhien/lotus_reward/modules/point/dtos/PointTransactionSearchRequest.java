package com.nguyenhien.lotus_reward.modules.point.dtos;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointTransactionSearchRequest {
    private UUID userId;
    private int page;
    private int size;
    private String sortBy;
    private String order;

    public Pageable toPageable() {
        // Validate the order parameter
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Create pageable object
        return PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
    }
}
