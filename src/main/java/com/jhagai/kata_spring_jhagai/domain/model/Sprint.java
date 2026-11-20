package com.jhagai.kata_spring_jhagai.domain.model;

import java.time.LocalDate;

public record Sprint(Long id, String name, SprintStatus status, LocalDate startDate, LocalDate endDate) {

    public Sprint withStatus(SprintStatus newStatus) {
        return new Sprint(id, name, newStatus, startDate, endDate);
    }
}
