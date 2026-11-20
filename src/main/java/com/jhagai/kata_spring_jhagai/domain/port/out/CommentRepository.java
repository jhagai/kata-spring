package com.jhagai.kata_spring_jhagai.domain.port.out;

import com.jhagai.kata_spring_jhagai.domain.model.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findByTicketId(Long ticketId);

    long countByTicketId(Long ticketId);
}
