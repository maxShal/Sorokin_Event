package com.example.Sorokin_Event.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class SignUpRequest{
        @NotBlank
        @Size(min = 5)
        private String login;
        @NotBlank
        @Size(min = 5)
        private String password;

        public SignUpRequest(String login, String password) {
                this.login = login;
                this.password = password;
        }

        public SignUpRequest() {
        }

        public String getLogin() {
                return login;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                SignUpRequest that = (SignUpRequest) o;
                return Objects.equals(login, that.login) && Objects.equals(password, that.password);
        }

        @Override
        public int hashCode() {
                return Objects.hash(login, password);
        }

        @Override
        public String toString() {
                return "SignUpRequest{" +
                        "login='" + login + '\'' +
                        ", password='" + password + '\'' +
                        '}';
        }
}
