package com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper;

import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.UserJpaEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserJpaEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getEmail());
    }
}
