package com.example.Sorokin_Event.entity;

import com.example.Sorokin_Event.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private Role role;
    private String login;
    private String passwordHash;

    public UserEntity(Long id, String login, String passwordHash, Role role ) {

        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public UserEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        UserEntity entity = (UserEntity) o;
        return Objects.equals(id, entity.id) && role == entity.role && Objects.equals(login, entity.login) && Objects.equals(passwordHash, entity.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, passwordHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

}
