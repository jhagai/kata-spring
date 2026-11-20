package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.SprintCloseSummary;

/**
 * Orchestration for this use case is already implemented; it delegates the pure
 * computation to SprintClosure.close, which is EXERCISE F (mutation vs pure functions).
 * See SUJET.md.
 */
public interface CloseSprintUseCase {

    SprintCloseSummary closeSprint(Long sprintId, Long nextSprintId);
}
