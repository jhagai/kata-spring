package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.User;

public record UserResponse(Long id, String username, String email) {

    public static UserResponse from(User user) {
        return new UserResponse(user.id(), user.username(), user.email());
    }
}
