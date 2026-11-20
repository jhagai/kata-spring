package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.TicketFull;

/**
 * EXERCISE C (MultipleBagFetchException). See SUJET.md.
 */
public interface GetTicketFullUseCase {

    TicketFull getFull(Long ticketId);
}
