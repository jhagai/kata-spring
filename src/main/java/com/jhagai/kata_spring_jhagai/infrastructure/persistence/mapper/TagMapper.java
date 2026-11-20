package com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper;

import com.jhagai.kata_spring_jhagai.domain.model.Tag;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TagJpaEntity;

public final class TagMapper {

    private TagMapper() {
    }

    public static Tag toDomain(TagJpaEntity entity) {
        return new Tag(entity.getId(), entity.getName());
    }
}
