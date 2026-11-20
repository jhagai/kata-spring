package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.Sprint;

import java.util.List;

public interface ListSprintsUseCase {

    List<Sprint> listAll();
}
