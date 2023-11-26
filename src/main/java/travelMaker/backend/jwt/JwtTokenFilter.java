package travelMaker.backend.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import travelMaker.backend.user.login.LoginUser;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("--------- JwtTokenFilter start ------------");
        log.info("Filter - Authorization 헤더 {}", request.getHeader("Authorization"));
        String token = jwtUtils.parseJwtToken(request);
        if (token != null && jwtUtils.validationJwtToken(token, response)) {
            LoginUser loginuser = jwtUtils.verify(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginuser, null, loginuser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        log.info("---------JwtTokenFilter end ------------");
        filterChain.doFilter(request, response);
    }
}
