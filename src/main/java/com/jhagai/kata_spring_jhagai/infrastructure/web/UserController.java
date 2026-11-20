package com.jhagai.kata_spring_jhagai.infrastructure.web;

import com.jhagai.kata_spring_jhagai.domain.port.in.CreateUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.GetUserUseCase;
import com.jhagai.kata_spring_jhagai.domain.port.in.ListUsersUseCase;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.CreateUserRequest;
import com.jhagai.kata_spring_jhagai.infrastructure.web.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase,
                           ListUsersUseCase listUsersUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = UserResponse.from(createUserUseCase.createUser(request.username(), request.email()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return UserResponse.from(getUserUseCase.getById(id));
    }

    @GetMapping
    public List<UserResponse> listAll() {
        return listUsersUseCase.listAll().stream().map(UserResponse::from).toList();
    }
}
