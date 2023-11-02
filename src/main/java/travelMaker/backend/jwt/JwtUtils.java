package travelMaker.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.ErrorResponse;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import static travelMaker.backend.jwt.JwtProperties.*;

@Slf4j
@Component
public class JwtUtils {
    private final Key key;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JwtUtils(@Value("${jwt.app.jwtSecretKey}")String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessTokenFromLoginUser(LoginUser loginUser) {
        return Jwts.builder()
                .claim("id", loginUser.getUser().getUserId())
                .claim("email",loginUser.getUser().getUserEmail())
                .claim("image",loginUser.getUser().getImageUrl())
                .claim("ageRange",loginUser.getUser().getUserAgeRange())
                .claim("gender",loginUser.getUser().getUserGender())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String parseJwtToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    public boolean validationJwtToken(String token, HttpServletResponse response) throws IOException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            setResponse(response, ErrorCode.INVALID_JWT_TOKEN, e);
        } catch (ExpiredJwtException e) {
            setResponse(response, ErrorCode.EXPIRED_ACCESS_TOKEN, e);
        } catch (UnsupportedJwtException e) {
            setResponse(response, ErrorCode.UNSUPPORTED_JWT_TOKEN, e);
        }
        return false;
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode, Exception e) throws IOException {
        log.error("error message {}", errorCode.getMessage(), e);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseDto<ErrorResponse> fail = ResponseDto.fail(errorCode.getStatus(), errorCode.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(fail));
    }

    public LoginUser verify(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        String image = claims.get("image",String.class);
        String ageRange = claims.get("ageRange", String.class);
        String gender = claims.get("gender", String.class);
        User user = User.builder()
                .userId(id)
                .userEmail(email)
                .imageUrl(image)
                .userAgeRange(ageRange)
                .userGender(gender)
                .build();
        //JWT 토큰필터에서 토큰을 파싱해서 유저정보를 만들어서 그 정보로 인증처리를한다.
        return new LoginUser(user);
    }

}
