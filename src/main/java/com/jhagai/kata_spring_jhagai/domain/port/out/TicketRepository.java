package com.jhagai.kata_spring_jhagai.domain.port.out;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.domain.model.TicketDetail;
import com.jhagai.kata_spring_jhagai.domain.model.TicketFull;
import com.jhagai.kata_spring_jhagai.domain.model.TicketSummary;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Optional<Ticket> findById(Long id);

    List<Ticket> findBySprintId(Long sprintId);

    boolean existsByKey(String key);

    Ticket addTag(Long ticketId, Long tagId);

    /**
     * EXERCISE A (lazy loading) implementation point.
     */
    Optional<TicketDetail> findDetailById(Long id);

    /**
     * EXERCISE B (N+1 queries) implementation point.
     */
    List<TicketSummary> findSummariesBySprintId(Long sprintId);

    /**
     * EXERCISE C (MultipleBagFetchException) implementation point.
     */
    Optional<TicketFull> findFullById(Long id);
}
