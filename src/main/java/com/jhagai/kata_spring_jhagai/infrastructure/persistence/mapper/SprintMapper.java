package com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.SprintJpaEntity;

public final class SprintMapper {

    private SprintMapper() {
    }

    public static Sprint toDomain(SprintJpaEntity entity) {
        return new Sprint(entity.getId(), entity.getName(), entity.getStatus(), entity.getStartDate(), entity.getEndDate());
    }
}
