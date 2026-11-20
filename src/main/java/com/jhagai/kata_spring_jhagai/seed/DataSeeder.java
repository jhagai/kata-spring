package com.jhagai.kata_spring_jhagai.seed;

import com.jhagai.kata_spring_jhagai.domain.model.SprintStatus;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddCommentToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddTagToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.port.out.SprintRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds a handful of users/sprints/tickets/comments/tags on startup so the .http
 * files always have real data to hit. Tickets are inserted directly via
 * {@link TicketRepository#save} with literal keys, deliberately bypassing
 * {@code TicketKeyGenerator} (EXERCISE D) so the app boots with usable data
 * regardless of whether that exercise has been solved yet.
 */
@Component
@Profile("!test")
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;
    private final TicketRepository ticketRepository;
    private final CreateUserUseCase createUserUseCase;
    private final CreateSprintUseCase createSprintUseCase;
    private final AddCommentToTicketUseCase addCommentToTicketUseCase;
    private final AddTagToTicketUseCase addTagToTicketUseCase;

    public DataSeeder(UserRepository userRepository,
                       SprintRepository sprintRepository,
                       TicketRepository ticketRepository,
                       CreateUserUseCase createUserUseCase,
                       CreateSprintUseCase createSprintUseCase,
                       AddCommentToTicketUseCase addCommentToTicketUseCase,
                       AddTagToTicketUseCase addTagToTicketUseCase) {
        this.userRepository = userRepository;
        this.sprintRepository = sprintRepository;
        this.ticketRepository = ticketRepository;
        this.createUserUseCase = createUserUseCase;
        this.createSprintUseCase = createSprintUseCase;
        this.addCommentToTicketUseCase = addCommentToTicketUseCase;
        this.addTagToTicketUseCase = addTagToTicketUseCase;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            return;
        }

        User alice = createUserUseCase.createUser("alice", "alice@kata.dev");
        User bob = createUserUseCase.createUser("bob", "bob@kata.dev");
        User carol = createUserUseCase.createUser("carol", "carol@kata.dev");

        Sprint sprint1 = createSprintUseCase.createSprint("Sprint 1", LocalDate.now().minusDays(7), LocalDate.now().plusDays(7));
        sprint1 = sprintRepository.save(sprint1.withStatus(SprintStatus.ACTIVE));
        Sprint sprint2 = createSprintUseCase.createSprint("Sprint 2", LocalDate.now().plusDays(8), LocalDate.now().plusDays(21));

        Ticket t1 = ticketRepository.save(new Ticket(null, "KATA-SEED-1", "Fix login redirect loop",
                "Users get stuck in a redirect loop after SSO login.", TicketStatus.DONE, alice.id(), sprint1.id()));
        Ticket t2 = ticketRepository.save(new Ticket(null, "KATA-SEED-2", "Add pagination to ticket list",
                "The sprint board should paginate past 50 tickets.", TicketStatus.IN_PROGRESS, bob.id(), sprint1.id()));
        ticketRepository.save(new Ticket(null, "KATA-SEED-3", "Investigate slow dashboard load",
                "Dashboard takes >5s to load for large sprints.", TicketStatus.TODO, carol.id(), sprint1.id()));
        ticketRepository.save(new Ticket(null, "KATA-SEED-4", "Allow ticket export to CSV",
                "Export the current sprint's tickets as a CSV file.", TicketStatus.TODO, null, sprint1.id()));
        ticketRepository.save(new Ticket(null, "KATA-SEED-5", "Design sprint velocity chart",
                "Mockups for a burndown-style velocity chart.", TicketStatus.TODO, alice.id(), sprint2.id()));
        ticketRepository.save(new Ticket(null, "KATA-SEED-6", "Spike: websocket notifications",
                "Evaluate feasibility of real-time ticket update notifications.", TicketStatus.IN_PROGRESS, bob.id(), sprint2.id()));

        addCommentToTicketUseCase.addComment(t1.id(), bob.id(), "Confirmed fixed on staging.");
        addCommentToTicketUseCase.addComment(t1.id(), carol.id(), "Nice, verified on prod too.");
        addCommentToTicketUseCase.addComment(t2.id(), alice.id(), "What page size are we targeting?");
        addCommentToTicketUseCase.addComment(t2.id(), bob.id(), "Going with 50 for now.");

        addTagToTicketUseCase.addTag(t1.id(), "bug");
        addTagToTicketUseCase.addTag(t1.id(), "backend");
        addTagToTicketUseCase.addTag(t2.id(), "frontend");
        addTagToTicketUseCase.addTag(t2.id(), "urgent");

        log.info("Seeded 3 users, 2 sprints, 6 tickets, 4 comments, 4 tag assignments.");
    }
}
