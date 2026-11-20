package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddCommentRequest(@NotNull Long authorId, @NotBlank String body) {
}
