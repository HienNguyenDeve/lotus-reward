package com.nguyenhien.lotus_reward.modules.point.services;

import org.springframework.data.domain.Page;

import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionResponse;
import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionSearchRequest;

public interface IPointTransactionService {
    Page<PointTransactionResponse> pagination(PointTransactionSearchRequest request);
}
