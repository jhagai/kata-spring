package com.jhagai.kata_spring_jhagai.domain.model;

import java.util.List;

/**
 * Read model combining both of a ticket's List-typed collections (comments and tags).
 */
public record TicketFull(Long id, String key, String title, List<Comment> comments, List<Tag> tags) {
}
