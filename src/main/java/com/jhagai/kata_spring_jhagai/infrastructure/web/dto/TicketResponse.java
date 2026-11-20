package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;

public record TicketResponse(
        Long id,
        String key,
        String title,
        String description,
        TicketStatus status,
        Long assigneeId,
        Long sprintId) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.id(), ticket.key(), ticket.title(), ticket.description(),
                ticket.status(), ticket.assigneeId(), ticket.sprintId());
    }
}
