package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.TicketDetail;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;

public record TicketDetailResponse(
        Long id,
        String key,
        String title,
        String description,
        TicketStatus status,
        String assigneeName,
        Long sprintId) {

    public static TicketDetailResponse from(TicketDetail detail) {
        return new TicketDetailResponse(
                detail.id(), detail.key(), detail.title(), detail.description(),
                detail.status(), detail.assigneeName(), detail.sprintId());
    }
}
