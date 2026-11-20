package com.jhagai.kata_spring_jhagai.domain.model;

/**
 * Read model for a single ticket, with the assignee's username already resolved
 * (so lazy associations never leak past the persistence adapter).
 */
public record TicketDetail(
        Long id,
        String key,
        String title,
        String description,
        TicketStatus status,
        String assigneeName,
        Long sprintId) {
}
