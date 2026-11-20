package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Comment;

public interface AddCommentToTicketUseCase {

    Comment addComment(Long ticketId, Long authorId, String body);
}
