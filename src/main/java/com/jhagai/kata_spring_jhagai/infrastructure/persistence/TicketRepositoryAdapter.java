package com.jhagai.kata_spring_jhagai.infrastructure.persistence;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketDetail;
import com.jhagai.kata_spring_jhagai.domain.model.TicketFull;
import com.jhagai.kata_spring_jhagai.domain.model.TicketSummary;
import com.jhagai.kata_spring_jhagai.domain.port.out.TicketRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.SprintJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TagJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TicketJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.UserJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper.TicketMapper;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.SprintJpaRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.TagJpaRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.TicketJpaRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryAdapter implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final SprintJpaRepository sprintJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final EntityManager entityManager;

    public TicketRepositoryAdapter(TicketJpaRepository ticketJpaRepository,
                                   UserJpaRepository userJpaRepository,
                                   SprintJpaRepository sprintJpaRepository,
                                   TagJpaRepository tagJpaRepository,
                                   EntityManager entityManager) {
        this.ticketJpaRepository = ticketJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.sprintJpaRepository = sprintJpaRepository;
        this.tagJpaRepository = tagJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Ticket save(Ticket ticket) {
        UserJpaEntity assigneeRef = ticket.assigneeId() == null ? null : userJpaRepository.getReferenceById(ticket.assigneeId());
        SprintJpaEntity sprintRef = ticket.sprintId() == null ? null : sprintJpaRepository.getReferenceById(ticket.sprintId());

        TicketJpaEntity entity;
        if (ticket.id() == null) {
            entity = new TicketJpaEntity(ticket.key(), ticket.title(), ticket.description(), ticket.status(), assigneeRef, sprintRef);
        } else {
            entity = ticketJpaRepository.findById(ticket.id())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown ticket id " + ticket.id()));
            entity.setTitle(ticket.title());
            entity.setDescription(ticket.description());
            entity.setStatus(ticket.status());
            entity.setAssignee(assigneeRef);
            entity.setSprint(sprintRef);
        }
        return TicketMapper.toDomain(ticketJpaRepository.save(entity));
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketJpaRepository.findById(id).map(TicketMapper::toDomain);
    }

    @Override
    public List<Ticket> findBySprintId(Long sprintId) {
        return ticketJpaRepository.findBySprintId(sprintId).stream().map(TicketMapper::toDomain).toList();
    }

    @Override
    public boolean existsByKey(String key) {
        return ticketJpaRepository.existsByKey(key);
    }

    @Override
    @Transactional
    public Ticket addTag(Long ticketId, Long tagId) {
        TicketJpaEntity entity = ticketJpaRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown ticket id " + ticketId));
        TagJpaEntity tagRef = tagJpaRepository.getReferenceById(tagId);
        if (entity.getTags().stream().noneMatch(tag -> tag.getId().equals(tagId))) {
            entity.getTags().add(tagRef);
        }
        return TicketMapper.toDomain(ticketJpaRepository.save(entity));
    }

    /**
     * EXERCISE A. See SUJET.md and TicketDetailLazyLoadingTest.
     */
    @Override
    public Optional<TicketDetail> findDetailById(Long id) {
        throw new UnsupportedOperationException("TODO exercise A: see SUJET.md");
    }

    /**
     * One {@link TicketSummary} per ticket of the sprint, assignee username resolved
     * ({@code null} if unassigned), total comment count per ticket.
     * <p>
     * EXERCISE B. See SUJET.md and TicketListForSprintQueryCountTest.
     */
    @Override
    public List<TicketSummary> findSummariesBySprintId(Long sprintId) {
        throw new UnsupportedOperationException("TODO exercise B: see SUJET.md");
    }

    /**
     * EXERCISE C. See SUJET.md and TicketFullFetchBagExceptionTest.
     */
    @Override
    public Optional<TicketFull> findFullById(Long id) {
        throw new UnsupportedOperationException("TODO exercise C: see SUJET.md");
    }
}
