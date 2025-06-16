package com.example.Sorokin_Event.dto;

import com.example.Sorokin_Event.entity.UserEntity;
import com.example.Sorokin_Event.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class UserDto {

    @NotNull
    private Long id;
    @NotNull
    private Role role;
    @NotBlank
    @Column(unique = true)
    private String login;
    @NotBlank
    private String passwordHash;

    public UserDto(Long id, String login, Role role, String passwordHash) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public UserDto() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return role == userDto.role && Objects.equals(login, userDto.login) && Objects.equals(passwordHash, userDto.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, login, passwordHash);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "role=" + role +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
