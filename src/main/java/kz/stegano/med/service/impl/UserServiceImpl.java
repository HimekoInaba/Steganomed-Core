package kz.stegano.med.service.impl;

import kz.stegano.med.exception.DefaultException;
import kz.stegano.med.model.Role;
import kz.stegano.med.model.User;
import kz.stegano.med.repository.UserRepository;
import kz.stegano.med.service.JwtTokenService;
import kz.stegano.med.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Override
    public String login(User user) {
        User existing = userRepository.findByUsername(user.getUsername());
        if (existing == null) {
            throw new DefaultException(String.format("user with username %s doesn`t exist", user.getUsername()));
        }

        Authentication authentication = authenticate(user.getUsername(), user.getPassword());
        return jwtTokenService.generateToken(authentication);
    }

    @Override
    public String create(User user) {
        User existing = userRepository.findByUsername(user.getUsername());
        if (existing != null) {
            throw new DefaultException(String.format("user with username %s already exists", user.getUsername()));
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String plainPassword = user.getPassword();
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setCreationTime(LocalDateTime.now());
        user.setPassword(encryptedPassword);
        user.addAuthority(new Role(Role.roles.PATIENT.name()));
        userRepository.saveAndFlush(user);

        Authentication authentication = authenticate(user.getUsername(), plainPassword);

        return jwtTokenService.generateToken(authentication);
    }

    @Override
    public Authentication authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.nonNull(user)) {
            return user;
        }
        throw new DefaultException(String.format("user with %s does not exist!", username));
    }

    @Override
    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new DefaultException(String.format("user with id %d does not exist!", id));
    }
}
