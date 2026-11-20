package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.TicketSummary;

public record TicketSummaryResponse(
        Long id,
        String key,
        String title,
        TicketStatus status,
        String assigneeName,
        long commentCount) {

    public static TicketSummaryResponse from(TicketSummary summary) {
        return new TicketSummaryResponse(
                summary.id(), summary.key(), summary.title(), summary.status(),
                summary.assigneeName(), summary.commentCount());
    }
}
