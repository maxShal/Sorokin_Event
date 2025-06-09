package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.entity.UserEntity;
import com.example.Sorokin_Event.mapper.UserMapper;
import com.example.Sorokin_Event.model.User;
import com.example.Sorokin_Event.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserMapper mapper;
    private UserRepository repository;

    public UserService(UserMapper mapper, UserRepository repository)
    {
        this.mapper = mapper;
        this.repository = repository;
    }

    public User createUser(User user)
    {
        return  mapper.toModel(repository.save(mapper.toEntity(mapper.toDto(user))));
    }

    public User findById(Long id)
    {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return mapper.toModel(entity);
    }
}
