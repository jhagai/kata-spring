package com.jhagai.kata_spring_jhagai.application;

import com.jhagai.kata_spring_jhagai.domain.exception.NotFoundException;
import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.model.SprintCloseSummary;
import com.jhagai.kata_spring_jhagai.domain.model.SprintStatistics;
import com.jhagai.kata_spring_jhagai.domain.model.SprintStatus;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.port.in.CloseSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetSprintStatisticsUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListSprintsUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.CommentRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.SprintRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SprintApplicationService implements CreateSprintUseCase, GetSprintUseCase, ListSprintsUseCase,
        GetSprintStatisticsUseCase, CloseSprintUseCase {

    private final SprintRepository sprintRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public SprintApplicationService(SprintRepository sprintRepository,
                                     TicketRepository ticketRepository,
                                     UserRepository userRepository,
                                     CommentRepository commentRepository) {
        this.sprintRepository = sprintRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Sprint createSprint(String name, LocalDate startDate, LocalDate endDate) {
        return sprintRepository.save(new Sprint(null, name, SprintStatus.PLANNED, startDate, endDate));
    }

    @Override
    public Sprint getById(Long id) {
        return sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint " + id + " not found"));
    }

    @Override
    public List<Sprint> listAll() {
        return sprintRepository.findAll();
    }

    /**
     * EXERCISE E. See SUJET.md and SprintStatisticsStreamTest.
     */
    @Override
    public SprintStatistics getStatistics(Long sprintId) {
        throw new UnsupportedOperationException("TODO exercise E: see SUJET.md");
    }

    @Override
    public SprintCloseSummary closeSprint(Long sprintId, Long nextSprintId) {
        Sprint sprintToClose = getById(sprintId);
        Sprint nextSprint = getById(nextSprintId);
        List<Ticket> ticketsInSprint = ticketRepository.findBySprintId(sprintId);

        SprintClosureResult result = SprintClosure.close(sprintToClose, ticketsInSprint, nextSprint);

        sprintRepository.save(result.closedSprint());
        List<Long> movedTicketIds = new ArrayList<>();
        for (Ticket ticket : result.ticketsToMove()) {
            Ticket saved = ticketRepository.save(ticket);
            movedTicketIds.add(saved.id());
        }
        return new SprintCloseSummary(sprintId, nextSprintId, movedTicketIds);
    }
}
