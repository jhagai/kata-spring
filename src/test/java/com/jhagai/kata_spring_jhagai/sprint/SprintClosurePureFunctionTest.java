package com.jhagai.kata_spring_jhagai.sprint;

import com.jhagai.kata_spring_jhagai.application.SprintClosure;
import com.jhagai.kata_spring_jhagai.application.SprintClosureResult;
import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.SprintStatus;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * EXERCISE F (mutation vs. pure functions). Plain JUnit test, no Spring context:
 * {@link SprintClosure#close} is a pure static function and should not need one.
 * The input ticket list is built with {@code List.copyOf(...)}, which is genuinely
 * immutable, so any in-place mutation attempt (e.g. {@code removeIf}, {@code sort})
 * fails loudly with {@code UnsupportedOperationException} instead of silently
 * corrupting the caller's data.
 */
class SprintClosurePureFunctionTest {

    @Test
    void closesSprintAndMovesUnfinishedTicketsWithoutMutatingInputs() {
        Sprint sprintToClose = new Sprint(1L, "Sprint 1", SprintStatus.ACTIVE, LocalDate.now(), LocalDate.now().plusDays(14));
        Sprint nextSprint = new Sprint(2L, "Sprint 2", SprintStatus.PLANNED, LocalDate.now().plusDays(15), LocalDate.now().plusDays(28));

        Ticket doneTicket = new Ticket(10L, "KATA-F-1", "Done ticket", "desc", TicketStatus.DONE, null, 1L);
        Ticket inProgressTicket = new Ticket(11L, "KATA-F-2", "In progress ticket", "desc", TicketStatus.IN_PROGRESS, null, 1L);
        Ticket todoTicket = new Ticket(12L, "KATA-F-3", "Todo ticket", "desc", TicketStatus.TODO, null, 1L);
        List<Ticket> ticketsInSprint = List.copyOf(List.of(doneTicket, inProgressTicket, todoTicket));
        List<Ticket> snapshotBeforeCall = List.copyOf(ticketsInSprint);

        SprintClosureResult result = SprintClosure.close(sprintToClose, ticketsInSprint, nextSprint);

        assertEquals(SprintStatus.CLOSED, result.closedSprint().status());
        assertEquals(sprintToClose.id(), result.closedSprint().id());
        assertEquals(sprintToClose.name(), result.closedSprint().name());

        Set<Long> movedIds = result.ticketsToMove().stream().map(Ticket::id).collect(Collectors.toSet());
        assertEquals(Set.of(11L, 12L), movedIds, "only the non-DONE tickets should move");
        for (Ticket moved : result.ticketsToMove()) {
            assertEquals(nextSprint.id(), moved.sprintId());
        }

        assertEquals(snapshotBeforeCall, ticketsInSprint, "the input ticket list must not be mutated");
        assertEquals(TicketStatus.DONE, doneTicket.status(), "input ticket instances must not be mutated");
        assertEquals(1L, doneTicket.sprintId(), "input ticket instances must not be mutated");
    }
}
