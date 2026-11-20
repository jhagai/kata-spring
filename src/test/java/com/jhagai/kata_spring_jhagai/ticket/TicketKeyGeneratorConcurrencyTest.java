package com.jhagai.kata_spring_jhagai.ticket;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateSprintUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateTicketUseCase;
import com.jhagai.kata_spring_jhagai.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * EXERCISE D (thread safety). Red until {@code SequentialTicketKeyGenerator.nextKey()}
 * hands out a unique, gap-free key to every one of many concurrent callers.
 */
class TicketKeyGeneratorConcurrencyTest extends AbstractIntegrationTest {

    private static final int THREAD_COUNT = 16;
    private static final int TICKET_COUNT = 100;
    private static final Pattern KEY_PATTERN = Pattern.compile("KATA-(\\d+)");

    @Autowired
    private CreateSprintUseCase createSprintUseCase;
    @Autowired
    private CreateTicketUseCase createTicketUseCase;

    @Test
    void generatesUniqueKeysUnderConcurrentCreation() throws InterruptedException {
        Sprint sprint = createSprintUseCase.createSprint("Sprint D", LocalDate.now(), LocalDate.now().plusDays(14));

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < TICKET_COUNT; i++) {
            tasks.add(() -> createTicketUseCase.createTicket(sprint.id(), "Concurrent ticket", "desc").key());
        }

        List<Future<String>> futures;
        try {
            futures = executor.invokeAll(tasks);
        } finally {
            executor.shutdown();
        }

        List<String> keys = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                keys.add(future.get());
            } catch (ExecutionException e) {
                fail("Ticket creation threw under concurrency: " + e.getCause());
            }
        }

        assertEquals(TICKET_COUNT, keys.size(), "every concurrent call should have produced a key");

        Set<String> uniqueKeys = new HashSet<>(keys);
        assertEquals(TICKET_COUNT, uniqueKeys.size(), "duplicate keys were generated under concurrency: " + keys);

        List<Long> numbers = new ArrayList<>();
        for (String key : keys) {
            Matcher matcher = KEY_PATTERN.matcher(key);
            if (!matcher.matches()) {
                fail("Key '" + key + "' does not match the expected KATA-<n> format");
            }
            numbers.add(Long.parseLong(matcher.group(1)));
        }

        long min = numbers.stream().mapToLong(Long::longValue).min().orElseThrow();
        long max = numbers.stream().mapToLong(Long::longValue).max().orElseThrow();
        assertEquals(TICKET_COUNT, max - min + 1,
                "generated key numbers should form a contiguous range with no lost increments, got: " + numbers);
    }
}
