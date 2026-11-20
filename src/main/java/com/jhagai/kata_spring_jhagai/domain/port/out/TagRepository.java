package com.jhagai.kata_spring_jhagai.domain.port.out;

import com.jhagai.kata_spring_jhagai.domain.model.Tag;

import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findByName(String name);
}
