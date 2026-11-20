package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Ticket;

public interface CreateTicketUseCase {

    Ticket createTicket(Long sprintId, String title, String description);
}
