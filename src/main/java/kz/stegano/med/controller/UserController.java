package kz.stegano.med.controller;

import kz.stegano.med.model.User;
import kz.stegano.med.model.dto.JwtAuthenticationResponse;
import kz.stegano.med.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String API = "/api";
    private static final String REGISTER = API + "/register";
    private static final String LOGIN = API + "/login";
    private static final String USER = API + "/user";
    private static final String USER_ID = USER + "/{id}";
    private static final String ME = USER + "/me";
    private static final String GET_BY_USERNAME = USER + "/by{username}";
    private static final String GET_ALL_USERS = USER + "by{role}";

    private final UserService userService;

    @PostMapping(LOGIN)
    public ResponseEntity login(@RequestBody User user) {
        String token = userService.login(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(REGISTER)
    public ResponseEntity register(@RequestBody User user) {
        String token = userService.create(user);
        return ResponseEntity.ok((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping(USER_ID)
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping(GET_BY_USERNAME)
    public User getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping(ME)
    public User getMe() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
