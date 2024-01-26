package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername (String accessToken);

    String generateAccessToken (UserDetails userDetails);

    String generateAccessToken (String email) throws NotFoundException;

    Boolean isAccessTokenValid (String accessToken, UserDetails userDetails);
}
