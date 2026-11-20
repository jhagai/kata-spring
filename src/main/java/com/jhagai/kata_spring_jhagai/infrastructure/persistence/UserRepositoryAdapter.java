package com.jhagai.kata_spring_jhagai.infrastructure.persistence;

import com.jhagai.kata_spring_jhagai.domain.model.User;
import com.jhagai.kata_spring_jhagai.domain.port.out.UserRepository;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.entity.UserJpaEntity;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.mapper.UserMapper;
import com.jhagai.kata_spring_jhagai.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity;
        if (user.id() == null) {
            entity = new UserJpaEntity(user.username(), user.email());
        } else {
            entity = userJpaRepository.findById(user.id())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown user id " + user.id()));
            entity.setUsername(user.username());
            entity.setEmail(user.email());
        }
        return UserMapper.toDomain(userJpaRepository.save(entity));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public List<User> findAllById(Collection<Long> ids) {
        return userJpaRepository.findAllById(ids).stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public long count() {
        return userJpaRepository.count();
    }
}
