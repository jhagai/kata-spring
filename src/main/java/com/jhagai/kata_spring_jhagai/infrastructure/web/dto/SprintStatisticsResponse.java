package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.SprintStatistics;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;

import java.util.Map;

public record SprintStatisticsResponse(
        Long sprintId,
        Map<TicketStatus, Long> countsByStatus,
        Map<String, Long> countsByAssignee,
        double averageCommentsPerTicket) {

    public static SprintStatisticsResponse from(SprintStatistics stats) {
        return new SprintStatisticsResponse(
                stats.sprintId(), stats.countsByStatus(), stats.countsByAssignee(), stats.averageCommentsPerTicket());
    }
}
