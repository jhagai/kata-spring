package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;

import java.time.LocalDate;

public interface CreateSprintUseCase {

    Sprint createSprint(String name, LocalDate startDate, LocalDate endDate);
}
