package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.entity.UserEntity;
import com.example.Sorokin_Event.mapper.UserMapper;
import com.example.Sorokin_Event.model.Role;
import com.example.Sorokin_Event.model.User;
import com.example.Sorokin_Event.repository.UserRepository;
import com.example.Sorokin_Event.security.SignUpRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper mapper, UserRepository repository, PasswordEncoder passwordEncoder)
    {
        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user)
    {
        return  mapper.toModel(repository.save(mapper.toEntity(mapper.toDto(user))));
    }

    public boolean isUserExistsByLogin(String login) {
        return repository.findByLogin(login).isPresent();
    }

    public User saveUser(User user)
    {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toModel(repository.save(entity));
    }

    public User registerUser(SignUpRequest sign)
    {
        if(repository.existsByLogin(sign.getLogin()))
        {
            throw new IllegalArgumentException("Пользователь уже есть");
        }
        var hashedPassword = passwordEncoder.encode(sign.getPassword());
        var userSave = new UserEntity(null, sign.getLogin(), hashedPassword, Role.USER);
        var saved = repository.save(userSave);

        return new User(saved.getId(),saved.getRole(),saved.getLogin(), saved.getPasswordHash());
    }

    public User findById(Long id)
    {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return mapper.toModel(entity);
    }

    public User findByLogin(String loginFromToken) {
        var user = repository.findByLogin(loginFromToken)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return mapper.toModel(user);
    }
}
