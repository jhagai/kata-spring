package com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository;

import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<TagJpaEntity, Long> {

    Optional<TagJpaEntity> findByName(String name);
}
