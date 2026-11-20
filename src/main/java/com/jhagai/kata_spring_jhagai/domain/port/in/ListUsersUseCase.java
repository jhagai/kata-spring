package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.User;

import java.util.List;

public interface ListUsersUseCase {

    List<User> listAll();
}
