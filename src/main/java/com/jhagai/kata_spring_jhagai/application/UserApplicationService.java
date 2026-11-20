package com.jhagai.kata_spring_jhagai.application;

import com.jhagai.kata_spring_jhagai.domain.exception.NotFoundException;
import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListUsersUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApplicationService implements CreateUserUseCase, GetUserUseCase, ListUsersUseCase {

    private final UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String username, String email) {
        return userRepository.save(new User(null, username, email));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User " + id + " not found"));
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }
}
