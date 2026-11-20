package com.jhagai.kata_spring_jhagai.domain.model;

import java.util.Map;

public record SprintStatistics(
        Long sprintId,
        Map<TicketStatus, Long> countsByStatus,
        Map<String, Long> countsByAssignee,
        double averageCommentsPerTicket) {
}
