package travelMaker.backend.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.jwt.JwtUtils;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.user.dto.request.ReissueRequestDto;
import travelMaker.backend.user.dto.response.LoginResponseDto;
import travelMaker.backend.user.login.KakaoProfile;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.login.OAuthToken;
import travelMaker.backend.user.model.PraiseBadge;
import travelMaker.backend.user.model.RefreshToken;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.RefreshTokenRepository;
import travelMaker.backend.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.UUID;

import static travelMaker.backend.common.error.ErrorCode.EXPIRED_REFRESH_TOKEN;

@Slf4j
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
    Double mannerScore = 36.5;

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

        String imageUrl = "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg";
        int atIndex = kakaoProfile.getKakao_account().getEmail().indexOf('@');
        String nickname = kakaoProfile.getKakao_account().getEmail().substring(0, atIndex);


        // 회원 가입 됐는지 확인
        User user = null;
        String gender = kakaoProfile.getKakao_account().getGender();
        if(gender == "female"){
            gender = "여자";
        } else if (gender == "male") {
            gender = "남자";
        }
        String ageRange = kakaoProfile.getKakao_account().getAge_range();
        if(ageRange == "10~19"){
            ageRange = "10대";
        } else if (ageRange == "20~29") {
            ageRange = "20대";
        } else if (ageRange == "30~39") {
            ageRange = "30대";
        } else if (ageRange == "40~49") {
            ageRange = "40대";
        } else if (ageRange == "50~59") {
            ageRange = "50대";
        } else if (ageRange == "60~69") {
            ageRange = "60대";
        } else if (ageRange == "70~79") {
            ageRange = "70대";
        } else if (ageRange == "80~89") {
            ageRange = "80대";
        } else if (ageRange == "90~99") {
            ageRange = "90대";
        }

        if(kakaoProfile.getKakao_account().getProfile() != null){
            imageUrl = kakaoProfile.getKakao_account().getProfile().profile_image_url;
        }


        if (!userRepository.existsByUserEmail(kakaoProfile.getKakao_account().getEmail())) {
            // 회원 가입

            user = User.builder()
                    .userEmail(kakaoProfile.getKakao_account().getEmail())
                    .password(passwordEncoder.encode("password"))
                    .imageUrl(imageUrl)
                    .userAgeRange(ageRange)
                    .userGender(gender)
                    .userName(kakaoProfile.getKakao_account().getName())
                    .nickname(nickname)
                    .mannerScore(mannerScore)
                    .signupDate(LocalDate.now())
                    .build();

            userRepository.save(user);
        }

        // 로그인
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoProfile.getKakao_account().getEmail(), "password"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);
        log.info("accessToken : {}", accessToken);
        RefreshToken refreshToken = RefreshToken.builder()
                .loginUser(loginUser)
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);

        return LoginResponseDto.builder()
                .userId(loginUser.getUser().getUserId())
                .email(loginUser.getUser().getUserEmail())
                .imageUrl(loginUser.getUser().getImageUrl())
                .username(loginUser.getUser().getUserName())
                .nickname(loginUser.getUser().getNickname())
                .ageRange(loginUser.getUser().getUserAgeRange())
                .gender(loginUser.getUser().getUserGender())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

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
