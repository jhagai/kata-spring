package com.jhagai.kata_spring_jhagai.domain.model;

import java.util.List;

public record SprintCloseSummary(Long closedSprintId, Long nextSprintId, List<Long> movedTicketIds) {
}
