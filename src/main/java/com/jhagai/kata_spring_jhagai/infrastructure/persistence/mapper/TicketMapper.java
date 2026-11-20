package com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TicketJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.UserJpaEntity;

public final class TicketMapper {

    private TicketMapper() {
    }

    /**
     * Only reads the assignee/sprint foreign-key ids, never their other fields, so
     * this is safe to call even when they are uninitialized LAZY proxies: a proxy's id
     * is always available without triggering a database hit. Resolving a human-readable
     * name (not just the id) is exactly what the TicketDetail/TicketSummary read models
     * are for (see EXERCISE A / EXERCISE B).
     */
    public static Ticket toDomain(TicketJpaEntity entity) {
        UserJpaEntity assignee = entity.getAssignee();
        return new Ticket(
                entity.getId(),
                entity.getKey(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                assignee == null ? null : assignee.getId(),
                entity.getSprint() == null ? null : entity.getSprint().getId());
    }
}
