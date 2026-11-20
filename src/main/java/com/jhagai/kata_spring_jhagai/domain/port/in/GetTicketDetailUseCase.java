package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.TicketDetail;

/**
 * EXERCISE A (lazy loading). See SUJET.md.
 */
public interface GetTicketDetailUseCase {

    TicketDetail getById(Long ticketId);
}
