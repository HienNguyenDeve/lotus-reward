package com.nguyenhien.lotus_reward.modules.point.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.nguyenhien.lotus_reward.modules.point.dtos.PointTransactionResponse;
import com.nguyenhien.lotus_reward.modules.point.entities.PointTransaction;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface IPointTransactionMapper {
    PointTransactionResponse toResponse(PointTransaction entity);
}
