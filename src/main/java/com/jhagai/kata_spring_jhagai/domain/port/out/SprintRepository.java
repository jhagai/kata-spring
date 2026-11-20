package com.jhagai.kata_spring_jhagai.domain.port.out;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;

import java.util.List;
import java.util.Optional;

public interface SprintRepository {

    Sprint save(Sprint sprint);

    Optional<Sprint> findById(Long id);

    List<Sprint> findAll();
}
