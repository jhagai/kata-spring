package com.jhagai.kata_spring_jhagai.sprint;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.SprintStatistics;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddCommentToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetSprintStatisticsUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * EXERCISE E (streams). Red until {@code SprintApplicationService.getStatistics}
 * computes per-status/per-assignee counts and the average comment count using the
 * Stream API.
 */
class SprintStatisticsStreamTest extends AbstractIntegrationTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private CreateSprintUseCase createSprintUseCase;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AddCommentToTicketUseCase addCommentToTicketUseCase;
    @Autowired
    private GetSprintStatisticsUseCase getSprintStatisticsUseCase;

    @Test
    void computesCountsAndAverageCommentsPerTicket() {
        User alice = createUserUseCase.createUser("alice", "alice@kata.dev");
        User bob = createUserUseCase.createUser("bob", "bob@kata.dev");
        Sprint sprint = createSprintUseCase.createSprint("Sprint E", LocalDate.now(), LocalDate.now().plusDays(14));

        Ticket t1 = createTicket("KATA-E-1", TicketStatus.DONE, alice.id(), sprint.id());
        Ticket t2 = createTicket("KATA-E-2", TicketStatus.DONE, alice.id(), sprint.id());
        Ticket t3 = createTicket("KATA-E-3", TicketStatus.IN_PROGRESS, bob.id(), sprint.id());
        Ticket t4 = createTicket("KATA-E-4", TicketStatus.IN_PROGRESS, bob.id(), sprint.id());
        createTicket("KATA-E-5", TicketStatus.TODO, null, sprint.id());

        addComments(t1, alice, 2);
        addComments(t2, alice, 1);
        addComments(t3, bob, 1);
        addComments(t4, bob, 2);

        SprintStatistics stats = getSprintStatisticsUseCase.getStatistics(sprint.id());

        assertEquals(sprint.id(), stats.sprintId());
        assertEquals(Map.of(TicketStatus.DONE, 2L, TicketStatus.IN_PROGRESS, 2L, TicketStatus.TODO, 1L),
                stats.countsByStatus());
        assertEquals(Map.of("alice", 2L, "bob", 2L, "unassigned", 1L), stats.countsByAssignee());
        assertEquals(1.2, stats.averageCommentsPerTicket(), 0.0001);
    }

    private Ticket createTicket(String key, TicketStatus status, Long assigneeId, Long sprintId) {
        return ticketRepository.save(new Ticket(null, key, key, "desc", status, assigneeId, sprintId));
    }

    private void addComments(Ticket ticket, User author, int count) {
        for (int i = 0; i < count; i++) {
            addCommentToTicketUseCase.addComment(ticket.id(), author.id(), "comment " + i);
        }
    }
}
