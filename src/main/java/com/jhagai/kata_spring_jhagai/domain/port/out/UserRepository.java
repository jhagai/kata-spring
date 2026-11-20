package com.jhagai.kata_spring_jhagai.domain.port.out;

import com.jhagai.kata_spring_jhagai.domain.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    List<User> findAllById(Collection<Long> ids);

    long count();
}
