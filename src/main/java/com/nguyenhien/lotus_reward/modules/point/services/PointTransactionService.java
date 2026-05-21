package com.nguyenhien.lotus_reward.modules.point.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionSearchRequest;
import com.nguyenhien.lotus_reward.modules.point.entities.PointTransaction;
import com.nguyenhien.lotus_reward.modules.point.mappers.IPointTransactionMapper;
import com.nguyenhien.lotus_reward.modules.point.repositories.IPointTransactionJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointTransactionService implements IPointTransactionService{
    private final IPointTransactionJpaRepository pointTransactionJpaRepository;
    private final IPointTransactionMapper pointTransactionMapper;

    @Override
    public Page<PointTransactionResponse> pagination(PointTransactionSearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        }
        Specification<PointTransaction> spec = (root, query, cb) -> cb.equal(root.get("user").get("id"), request.getUserId());
        var entities = pointTransactionJpaRepository.findAll(spec, request.toPageable());
        return entities.map(pointTransactionMapper::toResponse);
    }

}
