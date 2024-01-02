package travelMaker.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class RedisUtils {
    private final StringRedisTemplate stringRedisTemplate;

    public void saveCertificateNumber(String email, String authCode){
        stringRedisTemplate.opsForValue()
                .set(email, authCode, Duration.ofSeconds(5*60L));
    }
    public String getAuthCode(String email){
        return stringRedisTemplate.opsForValue().get(email);
    }
    public void deleteAuthCode(String email){
        stringRedisTemplate.delete(email);
    }
    public boolean existEmail(String email){
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(email));
    }
}
