package com.example.Sorokin_Event;

import com.example.Sorokin_Event.model.Role;
import com.example.Sorokin_Event.model.User;
import com.example.Sorokin_Event.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public DefaultUserInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void initUsers() {
        createUserIfNotExists("admin", "admin", Role.ADMIN);
        createUserIfNotExists("user", "user", Role.USER);
    }
    private void createUserIfNotExists(
            String login,
            String password,
            Role role
    ) {
        if (userService.isUserExistsByLogin(login)) {
            return;
        }
        var hashedPass = passwordEncoder.encode(password);
        var user = new User(
                null,
                role,
                login,
                hashedPass
        );
        userService.saveUser(user);
    }
}

