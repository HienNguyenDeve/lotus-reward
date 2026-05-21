package com.nguyenhien.lotus_reward.modules.point.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenhien.lotus_reward.modules.point.dtos.DeductPointRequest;
import com.nguyenhien.lotus_reward.modules.point.dtos.DeductPointResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionSearchRequest;
import com.nguyenhien.lotus_reward.modules.point.entities.PointTransaction;
import com.nguyenhien.lotus_reward.modules.point.enums.PointTransactionType;
import com.nguyenhien.lotus_reward.modules.point.mappers.IPointTransactionMapper;
import com.nguyenhien.lotus_reward.modules.point.repositories.IPointTransactionJpaRepository;
import com.nguyenhien.lotus_reward.modules.user.entities.User;
import com.nguyenhien.lotus_reward.modules.user.repositories.IUserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointTransactionService implements IPointTransactionService {
    private final IPointTransactionJpaRepository pointTransactionJpaRepository;
    private final IPointTransactionMapper pointTransactionMapper;
    private final IUserJpaRepository userJpaRepository;

    @Override
    public Page<PointTransactionResponse> pagination(PointTransactionSearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        }
        Specification<PointTransaction> spec = (root, query, cb) -> cb.equal(root.get("user").get("id"),
                request.getUserId());
        var entities = pointTransactionJpaRepository.findAll(spec, request.toPageable());
        return entities.map(pointTransactionMapper::toResponse);
    }

    @Override
    @Transactional
    public DeductPointResponse deductPoint(DeductPointRequest request) {
        User user = userJpaRepository
                .findByIdForUpdate(request.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found"));

        long currentPoint = user.getLotusPoint();

        long deductPoint = request.getPoint();

        if (currentPoint < deductPoint) {

            throw new RuntimeException(
                    "Insufficient point");
        }

        long remainingPoint = currentPoint - deductPoint;

        user.setLotusPoint(remainingPoint);

        PointTransaction transaction = new PointTransaction();

        transaction.setUser(user);

        transaction.setType(
                PointTransactionType.SPEND);

        transaction.setPoint(deductPoint);

        transaction.setBalanceBefore(
                currentPoint);

        transaction.setBalanceAfter(
                remainingPoint);

        transaction.setReferenceType(
                request.getReferenceType());

        pointTransactionJpaRepository.save(
                transaction);

        userJpaRepository.save(user);

        return DeductPointResponse.builder()
                .deductedPoint(deductPoint)
                .remainingPoint(remainingPoint)
                .build();
    }

}
