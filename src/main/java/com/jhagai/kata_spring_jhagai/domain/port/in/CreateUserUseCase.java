package com.jhagai.kata_spring_jhagai.domain.port.in;

import com.jhagai.kata_spring_jhagai.domain.model.User;

public interface CreateUserUseCase {

    User createUser(String username, String email);
}
