package com.jhagai.kata_spring_jhagai.application;

import com.jhagai.kata_spring_jhagai.domain.exception.NotFoundException;
import com.jhagai.kata_spring_jhagai.domain.model.Comment;
import com.jhagai.kata_spring_jhagai.domain.model.Tag;
import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketDetail;
import com.jhagai.kata_spring_jhagai.domain.model.TicketFull;
import com.jhagai.kata_spring_jhagai.domain.model.TicketStatus;
import com.jhagai.kata_spring_jhagai.domain.model.TicketSummary;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddCommentToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AddTagToTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.AssignTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateTicketUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetTicketDetailUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetTicketFullUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListTicketsForSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.MoveTicketToSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.CommentRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.TagRepository;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketKeyGenerator;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TicketApplicationService implements CreateTicketUseCase, AddCommentToTicketUseCase,
        AddTagToTicketUseCase, AssignTicketUseCase, MoveTicketToSprintUseCase, GetTicketDetailUseCase,
        ListTicketsForSprintUseCase, GetTicketFullUseCase {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final TicketKeyGenerator ticketKeyGenerator;

    public TicketApplicationService(TicketRepository ticketRepository,
                                     CommentRepository commentRepository,
                                     TagRepository tagRepository,
                                     TicketKeyGenerator ticketKeyGenerator) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
        this.ticketKeyGenerator = ticketKeyGenerator;
    }

    @Override
    public Ticket createTicket(Long sprintId, String title, String description) {
        String key = ticketKeyGenerator.nextKey();
        Ticket ticket = new Ticket(null, key, title, description, TicketStatus.TODO, null, sprintId);
        return ticketRepository.save(ticket);
    }

    @Override
    public Comment addComment(Long ticketId, Long authorId, String body) {
        requireTicket(ticketId);
        Comment comment = new Comment(null, ticketId, authorId, body, Instant.now());
        return commentRepository.save(comment);
    }

    @Override
    public Ticket addTag(Long ticketId, String tagName) {
        requireTicket(ticketId);
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(null, tagName)));
        return ticketRepository.addTag(ticketId, tag.id());
    }

    @Override
    public Ticket assign(Long ticketId, Long userId) {
        Ticket ticket = requireTicket(ticketId);
        return ticketRepository.save(ticket.withAssigneeId(userId));
    }

    @Override
    public Ticket moveToSprint(Long ticketId, Long sprintId) {
        Ticket ticket = requireTicket(ticketId);
        return ticketRepository.save(ticket.withSprintId(sprintId));
    }

    @Override
    public TicketDetail getById(Long ticketId) {
        return ticketRepository.findDetailById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket " + ticketId + " not found"));
    }

    @Override
    public List<TicketSummary> listForSprint(Long sprintId) {
        return ticketRepository.findSummariesBySprintId(sprintId);
    }

    @Override
    public TicketFull getFull(Long ticketId) {
        return ticketRepository.findFullById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket " + ticketId + " not found"));
    }

    private Ticket requireTicket(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket " + ticketId + " not found"));
    }
}
