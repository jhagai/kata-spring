package com.jhagai.kata_spring_jhagai.infrastructure.persistence;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;
import com.jhagai.kata_spring_jhagai.domain.port.out.SprintRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.SprintJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper.SprintMapper;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.SprintJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SprintRepositoryAdapter implements SprintRepository {

    private final SprintJpaRepository sprintJpaRepository;

    public SprintRepositoryAdapter(SprintJpaRepository sprintJpaRepository) {
        this.sprintJpaRepository = sprintJpaRepository;
    }

    @Override
    public Sprint save(Sprint sprint) {
        SprintJpaEntity entity;
        if (sprint.id() == null) {
            entity = new SprintJpaEntity(sprint.name(), sprint.status(), sprint.startDate(), sprint.endDate());
        } else {
            entity = sprintJpaRepository.findById(sprint.id())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown sprint id " + sprint.id()));
            entity.setName(sprint.name());
            entity.setStatus(sprint.status());
            entity.setStartDate(sprint.startDate());
            entity.setEndDate(sprint.endDate());
        }
        return SprintMapper.toDomain(sprintJpaRepository.save(entity));
    }

    @Override
    public Optional<Sprint> findById(Long id) {
        return sprintJpaRepository.findById(id).map(SprintMapper::toDomain);
    }

    @Override
    public List<Sprint> findAll() {
        return sprintJpaRepository.findAll().stream().map(SprintMapper::toDomain).toList();
    }
}
