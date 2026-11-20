package com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository;

import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TicketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<TicketJpaEntity, Long> {

    List<TicketJpaEntity> findBySprintId(Long sprintId);

    boolean existsByKey(String key);
}
