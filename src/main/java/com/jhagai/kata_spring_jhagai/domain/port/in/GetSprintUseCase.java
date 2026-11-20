package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;

public interface GetSprintUseCase {

    Sprint getById(Long id);
}
