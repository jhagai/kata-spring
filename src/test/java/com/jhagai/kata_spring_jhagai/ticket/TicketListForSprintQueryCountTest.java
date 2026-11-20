package com.jhagai.kata_spring_jhagai.ticket;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.TicketSummary;
import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddCommentToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AssignTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListTicketsForSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.support.AbstractIntegrationTest;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * EXERCISE B (N+1 queries). Red until {@code TicketRepositoryAdapter.findSummariesBySprintId}
 * resolves assignee names and comment counts in a constant number of SQL statements,
 * regardless of how many tickets are in the sprint.
 */
@TestPropertySource(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
class TicketListForSprintQueryCountTest extends AbstractIntegrationTest {

    private static final int TICKET_COUNT = 5;

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private CreateSprintUseCase createSprintUseCase;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AssignTicketUseCase assignTicketUseCase;
    @Autowired
    private AddCommentToTicketUseCase addCommentToTicketUseCase;
    @Autowired
    private ListTicketsForSprintUseCase listTicketsForSprintUseCase;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void queryCountStaysConstantRegardlessOfTicketCount() {
        User alice = createUserUseCase.createUser("alice", "alice@kata.dev");
        User bob = createUserUseCase.createUser("bob", "bob@kata.dev");
        Sprint sprint = createSprintUseCase.createSprint("Sprint B", LocalDate.now(), LocalDate.now().plusDays(14));

        for (int i = 0; i < TICKET_COUNT; i++) {
            Ticket ticket = ticketRepository.save(new Ticket(
                    null, "KATA-B-" + i, "Ticket " + i, "desc", TicketStatus.TODO, null, sprint.id()));
            ticket = assignTicketUseCase.assign(ticket.id(), i % 2 == 0 ? alice.id() : bob.id());
            addCommentToTicketUseCase.addComment(ticket.id(), alice.id(), "first comment");
            addCommentToTicketUseCase.addComment(ticket.id(), bob.id(), "second comment");
        }

        Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();

        List<TicketSummary> summaries = listTicketsForSprintUseCase.listForSprint(sprint.id());

        long queryCount = statistics.getPrepareStatementCount();
        assertTrue(queryCount <= 3,
                "Expected at most 3 SQL statements for " + TICKET_COUNT + " tickets but Hibernate ran "
                        + queryCount + " - check for N+1 queries");

        assertEquals(TICKET_COUNT, summaries.size());
        for (TicketSummary summary : summaries) {
            assertNotNull(summary.assigneeName(), "assignee name should be resolved for ticket " + summary.key());
            assertEquals(2L, summary.commentCount(), "comment count mismatch for ticket " + summary.key());
        }
    }
}
