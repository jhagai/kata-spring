package com.jhagai.kata_spring_jhagai.architecture;

import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TicketJpaEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Blocks the degenerate "fix" for EXERCISE A/B: switching the LAZY association to
 * EAGER instead of resolving it inside the right transaction boundary. Fails if
 * either {@code TicketJpaEntity.assignee} or {@code .sprint} is ever changed away
 * from {@code FetchType.LAZY}.
 */
class TicketAssigneeFetchTypeGuardTest {

    @Test
    void assigneeMustStayLazy() throws NoSuchFieldException {
        assertLazy("assignee");
    }

    @Test
    void sprintMustStayLazy() throws NoSuchFieldException {
        assertLazy("sprint");
    }

    private void assertLazy(String fieldName) throws NoSuchFieldException {
        Field field = TicketJpaEntity.class.getDeclaredField(fieldName);
        ManyToOne annotation = field.getAnnotation(ManyToOne.class);
        assertNotNull(annotation, "@ManyToOne annotation missing on field " + fieldName);
        assertEquals(FetchType.LAZY, annotation.fetch(),
                "TicketJpaEntity." + fieldName + " must stay FetchType.LAZY - resolve it inside the right "
                        + "transaction boundary instead of making it EAGER");
    }
}
