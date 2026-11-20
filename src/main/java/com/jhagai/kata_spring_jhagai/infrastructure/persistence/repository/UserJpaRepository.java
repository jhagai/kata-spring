package com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository;

import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
}
