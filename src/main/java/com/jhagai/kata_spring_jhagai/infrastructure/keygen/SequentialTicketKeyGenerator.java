package com.jhagai.kata_spring_jhagai.infrastructure.keygen;

import com.jhagai.kata_spring_jhagai.domain.port.out.TicketKeyGenerator;
import org.springframework.stereotype.Component;

/**
 * EXERCISE D. See SUJET.md and TicketKeyGeneratorConcurrencyTest.
 */
@Component
public class SequentialTicketKeyGenerator implements TicketKeyGenerator {

    @Override
    public String nextKey() {
        throw new UnsupportedOperationException("TODO exercise D: see SUJET.md");
    }
}
