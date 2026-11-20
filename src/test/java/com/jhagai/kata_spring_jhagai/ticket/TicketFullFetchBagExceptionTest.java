package com.jhagai.kata_spring_jhagai.ticket;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.Tag;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketFull;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddCommentToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddTagToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetTicketFullUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * EXERCISE C (MultipleBagFetchException). Red until
 * {@code TicketRepositoryAdapter.findFullById} loads both the comments and the tags
 * of a ticket without throwing {@code org.hibernate.loader.MultipleBagFetchException}.
 */
class TicketFullFetchBagExceptionTest extends AbstractIntegrationTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private CreateSprintUseCase createSprintUseCase;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AddCommentToTicketUseCase addCommentToTicketUseCase;
    @Autowired
    private AddTagToTicketUseCase addTagToTicketUseCase;
    @Autowired
    private GetTicketFullUseCase getTicketFullUseCase;

    @Test
    void loadsCommentsAndTagsTogether() {
        User alice = createUserUseCase.createUser("alice", "alice@kata.dev");
        User bob = createUserUseCase.createUser("bob", "bob@kata.dev");
        Sprint sprint = createSprintUseCase.createSprint("Sprint C", LocalDate.now(), LocalDate.now().plusDays(14));

        Ticket ticket = ticketRepository.save(
                new Ticket(null, "KATA-C-1", "Title", "Desc", TicketStatus.TODO, null, sprint.id()));

        addCommentToTicketUseCase.addComment(ticket.id(), alice.id(), "comment 1");
        addCommentToTicketUseCase.addComment(ticket.id(), bob.id(), "comment 2");
        addCommentToTicketUseCase.addComment(ticket.id(), alice.id(), "comment 3");

        addTagToTicketUseCase.addTag(ticket.id(), "bug");
        addTagToTicketUseCase.addTag(ticket.id(), "backend");

        TicketFull full = getTicketFullUseCase.getFull(ticket.id());

        assertEquals(ticket.id(), full.id());
        assertEquals("KATA-C-1", full.key());
        assertEquals(3, full.comments().size());
        assertEquals(List.of("comment 1", "comment 2", "comment 3"),
                full.comments().stream().map(c -> c.body()).toList());
        assertEquals(2, full.tags().size());
        assertEquals(Set.of("bug", "backend"), full.tags().stream().map(Tag::name).collect(Collectors.toSet()));
    }
}
