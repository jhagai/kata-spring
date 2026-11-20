package com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper;

import com.jhagai.kata_spring_jhagai.domain.model.Comment;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.CommentJpaEntity;

public final class CommentMapper {

    private CommentMapper() {
    }

    /**
     * Only reads the ticket/author foreign-key ids, never their other fields, so this
     * is safe to call even when {@code ticket}/{@code author} are uninitialized LAZY
     * proxies: a proxy's id is always available without triggering a database hit.
     */
    public static Comment toDomain(CommentJpaEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getTicket().getId(),
                entity.getAuthor().getId(),
                entity.getBody(),
                entity.getCreatedAt());
    }
}
