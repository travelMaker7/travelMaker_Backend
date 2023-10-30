package travelMaker.backend.user.repository;

import org.springframework.data.repository.CrudRepository;
import travelMaker.backend.user.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
