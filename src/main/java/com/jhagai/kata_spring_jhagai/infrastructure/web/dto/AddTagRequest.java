package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AddTagRequest(@NotBlank String name) {
}
