package com.nguyenhien.lotus_reward.modules.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileCreateRequest;
import com.nguyenhien.lotus_reward.modules.user.dtos.UserProfileResponse;
import com.nguyenhien.lotus_reward.modules.user.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface IUserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserProfileCreateRequest request);
    UserProfileResponse toResponse(User entity);
}
