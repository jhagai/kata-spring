package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;

public record AssignTicketRequest(@NotNull Long userId) {
}
