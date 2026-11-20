package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;

public interface AddTagToTicketUseCase {

    Ticket addTag(Long ticketId, String tagName);
}
