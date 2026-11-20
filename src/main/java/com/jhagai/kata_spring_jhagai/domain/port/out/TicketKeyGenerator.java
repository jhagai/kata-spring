package com.jhagai.kata_spring_jhagai.domain.port.out;

/**
 * EXERCISE D (thread safety) implementation point: {@code nextKey()} is called
 * concurrently whenever tickets are created simultaneously and must never hand out
 * the same key twice, nor lose increments under contention.
 */
public interface TicketKeyGenerator {

    String nextKey();
}
