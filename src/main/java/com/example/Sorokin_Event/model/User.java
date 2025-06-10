package com.example.Sorokin_Event.model;

import java.util.Objects;

public class User {

    private Long id;
    private Role role;
    private String login;
    private String passwordHash;

    public User(Long id, Role role, String login, String passwordHash) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public User(){}

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
        User user = (User) o;
        return Objects.equals(id, user.id) && role == user.role && Objects.equals(login, user.login) && Objects.equals(passwordHash, user.passwordHash);
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
