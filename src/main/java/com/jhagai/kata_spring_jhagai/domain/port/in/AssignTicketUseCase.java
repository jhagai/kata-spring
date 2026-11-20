package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;

public interface AssignTicketUseCase {

    Ticket assign(Long ticketId, Long userId);
}
