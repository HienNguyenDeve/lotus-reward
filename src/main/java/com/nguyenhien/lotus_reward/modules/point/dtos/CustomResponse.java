package com.nguyenhien.lotus_reward.modules.point.dtos;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private Collection<EntityModel<T>> data;

    private PagedModel.PageMetadata page;

    private Links links;
}
