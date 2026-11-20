package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.TicketSummary;

import java.util.List;

/**
 * Returns one {@link TicketSummary} per ticket of the given sprint, with the
 * assignee's username resolved ({@code null} if unassigned) and the ticket's total
 * comment count.
 * <p>
 * EXERCISE B (N+1 queries). See SUJET.md.
 */
public interface ListTicketsForSprintUseCase {

    List<TicketSummary> listForSprint(Long sprintId);
}
