package com.nguyenhien.lotus_reward.modules.point.services;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.nguyenhien.lotus_reward.modules.point.dtos.DeductPointRequest;
import com.nguyenhien.lotus_reward.modules.point.dtos.DeductPointResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionSearchRequest;

public interface IPointTransactionService {
    Page<PointTransactionResponse> pagination(PointTransactionSearchRequest request);

    DeductPointResponse deductPoint(
            DeductPointRequest request);
}
