package com.jhagai.kata_spring_jhagai.infrastructure.persistence;

import com.jhagai.kata_spring_jhagai.domain.model.Tag;
import com.jhagai.kata_spring_jhagai.domain.port.out.TagRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.TagJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper.TagMapper;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.TagJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepositoryAdapter implements TagRepository {

    private final TagJpaRepository tagJpaRepository;

    public TagRepositoryAdapter(TagJpaRepository tagJpaRepository) {
        this.tagJpaRepository = tagJpaRepository;
    }

    @Override
    public Tag save(Tag tag) {
        TagJpaEntity entity = new TagJpaEntity(tag.name());
        return TagMapper.toDomain(tagJpaRepository.save(entity));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagJpaRepository.findByName(name).map(TagMapper::toDomain);
    }
}
