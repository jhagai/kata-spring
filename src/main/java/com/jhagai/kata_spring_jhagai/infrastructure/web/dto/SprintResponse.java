package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.SprintStatus;

import java.time.LocalDate;

public record SprintResponse(Long id, String name, SprintStatus status, LocalDate startDate, LocalDate endDate) {

    public static SprintResponse from(Sprint sprint) {
        return new SprintResponse(sprint.id(), sprint.name(), sprint.status(), sprint.startDate(), sprint.endDate());
    }
}
