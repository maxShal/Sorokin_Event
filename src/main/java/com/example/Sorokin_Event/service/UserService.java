package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.entity.UserEntity;
import com.example.Sorokin_Event.mapper.UserMapper;
import com.example.Sorokin_Event.model.Role;
import com.example.Sorokin_Event.model.User;
import com.example.Sorokin_Event.repository.UserRepository;
import com.example.Sorokin_Event.security.SignUpRequest;
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

    public User registerUser(SignUpRequest sign)
    {
        if(repository.existsByLogin(sign.getLogin()))
        {
            throw new IllegalArgumentException("Пользователь уже есть");
        }
        var userSave = new UserEntity(null, sign.getLogin(), sign.getPassword(), Role.USER);
        var saved = repository.save(userSave);

        return new User(saved.getId(),saved.getRole(),saved.getLogin(), saved.getPasswordHash());
    }

    public User findById(Long id)
    {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return mapper.toModel(entity);
    }
}
