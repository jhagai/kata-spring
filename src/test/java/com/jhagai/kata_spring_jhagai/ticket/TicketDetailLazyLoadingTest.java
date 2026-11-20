package com.jhagai.kata_spring_jhagai.ticket;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketDetail;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.in.AssignTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetTicketDetailUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * EXERCISE A (lazy loading). Red until {@code TicketRepositoryAdapter.findDetailById}
 * resolves the assignee's username without leaking a LazyInitializationException.
 */
class TicketDetailLazyLoadingTest extends AbstractIntegrationTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private CreateSprintUseCase createSprintUseCase;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AssignTicketUseCase assignTicketUseCase;
    @Autowired
    private GetTicketDetailUseCase getTicketDetailUseCase;

    @Test
    void returnsAssigneeUsernameWithoutLazyInitializationException() {
        User dave = createUserUseCase.createUser("dave", "dave@kata.dev");
        Sprint sprint = createSprintUseCase.createSprint("Sprint A", LocalDate.now(), LocalDate.now().plusDays(14));

        Ticket ticket = ticketRepository.save(
                new Ticket(null, "KATA-A-1", "Fix the thing", "A description", TicketStatus.TODO, null, sprint.id()));
        Ticket assigned = assignTicketUseCase.assign(ticket.id(), dave.id());

        TicketDetail detail = getTicketDetailUseCase.getById(assigned.id());

        assertEquals(assigned.id(), detail.id());
        assertEquals("KATA-A-1", detail.key());
        assertEquals("dave", detail.assigneeName());
        assertEquals(sprint.id(), detail.sprintId());
    }
}
