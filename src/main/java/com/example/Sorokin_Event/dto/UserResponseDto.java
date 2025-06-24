package com.example.Sorokin_Event.dto;

import com.example.Sorokin_Event.model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class UserResponseDto
{

    @NotNull
    private Long id;
    @NotNull
    private Role role;
    @NotBlank
    @Column(unique = true)
    private String login;
    @NotBlank
    private String passwordHash;

    public UserResponseDto(Long id, Role role, String login, String passwordHash) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.passwordHash = passwordHash;
    }
    public UserResponseDto(){}

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
        UserResponseDto that = (UserResponseDto) o;
        return Objects.equals(id, that.id) && role == that.role && Objects.equals(login, that.login) && Objects.equals(passwordHash, that.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, passwordHash);
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
