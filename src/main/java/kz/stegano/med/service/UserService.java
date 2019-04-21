package kz.stegano.med.service;

import kz.stegano.med.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    String login(User user);

    String create(User user);

    Authentication authenticate(String username, String password);

    User getById(Long id);

    User getByUsername(String username);

    //    List<User> getAllUsersByRole(String  role);
}
