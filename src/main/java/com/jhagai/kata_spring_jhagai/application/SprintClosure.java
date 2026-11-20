package com.jhagai.kata_spring_jhagai.application;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;

import java.util.List;

/**
 * EXERCISE F. See SUJET.md and SprintClosurePureFunctionTest.
 */
public final class SprintClosure {

    private SprintClosure() {
    }

    public static SprintClosureResult close(Sprint sprintToClose, List<Ticket> ticketsInSprint, Sprint nextSprint) {
        throw new UnsupportedOperationException("TODO exercise F: see SUJET.md");
    }
}
