package travelMaker.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.common.error.ErrorCode;

import java.io.IOException;
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException.getClass().equals(UsernameNotFoundException.class)) {
            setResponse(response, ErrorCode.USERNAME_NOT_FOUND);
        } else if (authException.getClass().equals(BadCredentialsException.class)) {
            setResponse(response, ErrorCode.BAD_CREDENTIALS);
        }

    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseDto<Void> fail = ResponseDto.fail(errorCode.getStatus().toString(), errorCode.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(fail));
    }
}
