package com.jhagai.kata_spring_jhagai.infrastructure.web.dto;

import com.jhagai.kata_spring_jhagai.domain.model.TicketFull;

import java.util.List;

public record TicketFullResponse(Long id, String key, String title, List<CommentResponse> comments, List<String> tags) {

    public static TicketFullResponse from(TicketFull full) {
        return new TicketFullResponse(
                full.id(),
                full.key(),
                full.title(),
                full.comments().stream().map(CommentResponse::from).toList(),
                full.tags().stream().map(com.jhagai.kata_spring_jhagai.domain.model.Tag::name).toList());
    }
}
