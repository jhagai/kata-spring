package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.Comment;

import java.time.Instant;

public record CommentResponse(Long id, Long ticketId, Long authorId, String body, Instant createdAt) {

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.id(), comment.ticketId(), comment.authorId(), comment.body(), comment.createdAt());
    }
}
