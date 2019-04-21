package kz.stegano.med.service;

import kz.stegano.med.model.User;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {
    String generateToken(Authentication authentication);

    boolean validateToken(String authToken);

    User getUserFromJWT(String token);
}
