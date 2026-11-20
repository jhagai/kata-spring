package com.jhagai.kata_spring_jhagai.architecture;

import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TicketJpaEntity;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Blocks the degenerate "fix" for EXERCISE C: converting {@code comments} or
 * {@code tags} to a {@code Set} to dodge MultipleBagFetchException instead of
 * running two JOIN FETCH queries. Fails if either field's declared type changes
 * away from {@code java.util.List}.
 */
class TicketCollectionTypesGuardTest {

    @Test
    void commentsMustStayAList() throws NoSuchFieldException {
        assertListTyped("comments");
    }

    @Test
    void tagsMustStayAList() throws NoSuchFieldException {
        assertListTyped("tags");
    }

    private void assertListTyped(String fieldName) throws NoSuchFieldException {
        Field field = TicketJpaEntity.class.getDeclaredField(fieldName);
        assertEquals(List.class, field.getType(),
                "TicketJpaEntity." + fieldName + " must stay a java.util.List - do not switch to Set to dodge "
                        + "MultipleBagFetchException");
    }
}
