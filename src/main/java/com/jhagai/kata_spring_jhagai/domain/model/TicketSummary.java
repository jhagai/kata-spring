package com.jhagai.kata_spring_jhagai.domain.model;

/**
 * Read model for listing tickets of a sprint without triggering N+1 queries.
 */
public record TicketSummary(
        Long id,
        String key,
        String title,
        TicketStatus status,
        String assigneeName,
        long commentCount) {
}
