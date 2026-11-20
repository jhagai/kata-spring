package com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository;

import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.SprintJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintJpaRepository extends JpaRepository<SprintJpaEntity, Long> {
}
