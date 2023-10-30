package travelMaker.backend.user.model;


import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;
import travelMaker.backend.user.login.LoginUser;

import static travelMaker.backend.jwt.JwtProperties.REFRESH_TOKEN_EXPIRE_TIME_FOR_REDIS;

@RedisHash(value = "refresh")
@Getter
public class RefreshToken {
    @Id
    private Long id;
    private LoginUser loginUser;
    @TimeToLive
    private Long expiration = REFRESH_TOKEN_EXPIRE_TIME_FOR_REDIS;
    @Indexed
    private String refreshToken;

    @Builder
    public RefreshToken(LoginUser loginUser, String refreshToken) {
        this.loginUser = loginUser;
        this.refreshToken = refreshToken;
    }
}