package com.jhagai.kata_spring_jhagai.domain.model;

import java.time.Instant;

public record Comment(Long id, Long ticketId, Long authorId, String body, Instant createdAt) {
}
