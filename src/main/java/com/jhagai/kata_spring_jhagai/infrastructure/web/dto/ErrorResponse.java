package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

public record ErrorResponse(int status, String error, String message) {
}
