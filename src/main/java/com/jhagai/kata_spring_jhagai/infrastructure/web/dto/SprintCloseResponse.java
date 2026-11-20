package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.SprintCloseSummary;

import java.util.List;

public record SprintCloseResponse(Long closedSprintId, Long nextSprintId, List<Long> movedTicketIds) {

    public static SprintCloseResponse from(SprintCloseSummary summary) {
        return new SprintCloseResponse(summary.closedSprintId(), summary.nextSprintId(), summary.movedTicketIds());
    }
}
