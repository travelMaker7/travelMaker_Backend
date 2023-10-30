package travelMaker.backend.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.jwt.JwtUtils;
import travelMaker.backend.user.dto.request.ReissueRequestDto;
import travelMaker.backend.user.dto.response.LoginResponseDto;
import travelMaker.backend.user.login.KakaoProfile;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.login.OAuthToken;
import travelMaker.backend.user.model.RefreshToken;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.RefreshTokenRepository;
import travelMaker.backend.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static travelMaker.backend.common.error.ErrorCode.EXPIRED_REFRESH_TOKEN;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-url}")
    private String redirectId;
    @Value("${kakao.token-uri}")
    private String tokenUri;
    @Value("${kakao.user-info-uri}")
    private String UserInfoUri;
    @Value("${kakao.grant-type}")
    private String grantType;

   private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    public LoginResponseDto login(String code) throws JsonProcessingException {
        // 토큰 받아오기
        RestTemplate tokenRt = new RestTemplate();
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
        tokenParams.add("grant_type", grantType);
        tokenParams.add("client_id", clientId);
        tokenParams.add("redirect_uri", redirectId);
        tokenParams.add("code", code);
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenParams, tokenHeaders);
        ResponseEntity<String> response = tokenRt.exchange(
                tokenUri,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );
        OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

        // 카카오 유저 정보 받아오기
        RestTemplate profileRt = new RestTemplate();
        MultiValueMap<String, String> profileParams = new LinkedMultiValueMap<>();
        profileParams.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(profileParams);
        ResponseEntity<String> infoResponse = profileRt.exchange(
                UserInfoUri,
                HttpMethod.GET,
                profileRequest,
                String.class
        );
        KakaoProfile kakaoProfile = objectMapper.readValue(infoResponse.getBody(), KakaoProfile.class);

        // 회원 가입 됐는지 확인
        User user = null;
        if (!userRepository.existsByUserEmail(kakaoProfile.getKakao_account().getEmail())) {
            // 회원 가입
            user = User.builder()
                    .userEmail(kakaoProfile.getKakao_account().getEmail())
                    .password(passwordEncoder.encode("password"))
                    .imageUrl(kakaoProfile.getKakao_account().getProfile().getProfile_image_url())
                    .userAgeRange(kakaoProfile.getKakao_account().getAge_range())
                    .userGender(kakaoProfile.getKakao_account().getGender())
                    .userName(kakaoProfile.getKakao_account().getName())
                    .signupDate(LocalDate.now())
                    .build();

            userRepository.save(user);
        }

        // 로그인
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoProfile.getKakao_account().getEmail(), "password"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);

        RefreshToken refreshToken = RefreshToken.builder()
                .loginUser(loginUser)
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .userId(loginUser.getUser().getUserId())
                .email(loginUser.getUser().getUserEmail())
                .imageUrl(loginUser.getUser().getImageUrl())
                .username(loginUser.getUser().getUserName())
                .ageRange(loginUser.getUser().getUserAgeRange())
                .gender(loginUser.getUser().getUserGender())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        return loginResponse;
    }

    public LoginResponseDto reissue(ReissueRequestDto request) {
        String refreshToken = request.getRefreshToken();

        RefreshToken findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new GlobalException(EXPIRED_REFRESH_TOKEN));
        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(findRefreshToken.getLoginUser());

        return LoginResponseDto.builder()
                .userId(findRefreshToken.getLoginUser().getUser().getUserId())
                .email(findRefreshToken.getLoginUser().getUser().getUserEmail())
                .imageUrl(findRefreshToken.getLoginUser().getUser().getImageUrl())
                .username(findRefreshToken.getLoginUser().getUser().getUserName())
                .ageRange(findRefreshToken.getLoginUser().getUser().getUserAgeRange())
                .gender(findRefreshToken.getLoginUser().getUser().getUserGender())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
