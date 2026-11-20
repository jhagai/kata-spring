package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.SprintStatistics;

/**
 * EXERCISE E (Streams). See SUJET.md.
 */
public interface GetSprintStatisticsUseCase {

    SprintStatistics getStatistics(Long sprintId);
}
