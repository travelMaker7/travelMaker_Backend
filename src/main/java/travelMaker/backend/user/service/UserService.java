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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.config.RedisUtils;
import travelMaker.backend.jwt.JwtUtils;
import travelMaker.backend.user.dto.request.*;
import travelMaker.backend.user.dto.response.LoginResponseDto;
import travelMaker.backend.user.login.KakaoProfile;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.login.OAuthToken;
import travelMaker.backend.user.model.PlatformType;
import travelMaker.backend.user.model.RefreshToken;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.RefreshTokenRepository;
import travelMaker.backend.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Value("${spring.mail.username}")
    private String sendEmail;
    private final JavaMailSender mailSender;
    private final RedisUtils redisUtils;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Transactional
    public LoginResponseDto kakaoLogin(String code) throws JsonProcessingException {

        // 토큰 받아오기
        OAuthToken oAuthToken = getOAuthTokenFromKakao(code);

        // 카카오 유저 정보 받아오기
        KakaoProfile kakaoProfile = getKakaoProfileFromKakao(oAuthToken);

        String imageUrl = "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg";
        int atIndex = kakaoProfile.getKakao_account().getEmail().indexOf('@');
        String nickname = kakaoProfile.getKakao_account().getEmail().substring(0, atIndex);

        String gender = convertWord(kakaoProfile);
        String ageRange = getAgeRange(kakaoProfile);
        // kakao유저 비밀번호 생성
        String password = "KAKAO_ID" + kakaoProfile.getId();

        if(kakaoProfile.getKakao_account().getProfile() != null){
            imageUrl = kakaoProfile.getKakao_account().getProfile().profile_image_url;
        }


        if (!userRepository.existsByUserEmail(kakaoProfile.getKakao_account().getEmail())) {
            // 회원 가입
            String birthyear = kakaoProfile.getKakao_account().getBirthyear();
            String birthday = kakaoProfile.getKakao_account().getBirthday();
            LocalDate birth = birthdayConverter(birthyear, birthday);

            User user = User.builder()
                    .userEmail(kakaoProfile.getKakao_account().getEmail())
                    .password(passwordEncoder.encode(password))
                    .imageUrl(imageUrl)
                    .userAgeRange(ageRange)
                    .userGender(gender)
                    .userName(kakaoProfile.getKakao_account().getName())
                    .nickname(nickname)
                    .signupDate(LocalDate.now())
                    .birth(birth)
                    .platformType(PlatformType.KAKAO)
                    .build();

            userRepository.save(user);
        }



        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoProfile.getKakao_account().getEmail(), password)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);

        RefreshToken refreshToken = RefreshToken.builder()
                .loginUser(loginUser)
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);

        return LoginResponseDto.builder()
                .userId(loginUser.getUser().getUserId())
                .imageUrl(loginUser.getUser().getImageUrl())
                .email(loginUser.getUser().getUserEmail())
                .nickname(loginUser.getUser().getNickname())
                .username(loginUser.getUser().getUserName())
                .ageRange(loginUser.getUser().getUserAgeRange())
                .gender(loginUser.getUser().getUserGender())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    private String convertWord(KakaoProfile kakaoProfile) {
        String gender = kakaoProfile.getKakao_account().getGender();
        if(gender.equals("female")){
            gender = "여자";
        } else if (gender.equals("male")) {
            gender = "남자";
        }
        return gender;
    }

    private String getAgeRange(KakaoProfile kakaoProfile) {
        String ageRange = kakaoProfile.getKakao_account().getAge_range();
        if(ageRange.equals("10~19")){
            ageRange = "10대";
        } else if (ageRange.equals("20~29")) {
            ageRange = "20대";
        } else if (ageRange.equals("30~39")) {
            ageRange = "30대";
        } else if (ageRange.equals("40~49")) {
            ageRange = "40대";
        } else if (ageRange.equals("50~59")) {
            ageRange = "50대";
        } else if (ageRange.equals("60~69")) {
            ageRange = "60대";
        } else if (ageRange.equals("70~79")) {
            ageRange = "70대";
        } else if (ageRange.equals("80~89")) {
            ageRange = "80대";
        } else if (ageRange.equals("90~99")) {
            ageRange = "90대";
        }
        return ageRange;
    }

    private KakaoProfile getKakaoProfileFromKakao(OAuthToken oAuthToken) throws JsonProcessingException {
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
        return objectMapper.readValue(infoResponse.getBody(), KakaoProfile.class);
    }

    private OAuthToken getOAuthTokenFromKakao(String code) throws JsonProcessingException {
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
        return objectMapper.readValue(response.getBody(), OAuthToken.class);
    }

    private LocalDate birthdayConverter(String birthyear, String birthday) {
        String birthString = birthyear + "-" + birthday.substring(0, 2) + "-" + birthday.substring(2);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthString, format);
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

    @Transactional
    public void signup(SignupRequestDto signupDto) {
        if(!signupDto.isEmailValid()) {
            throw new GlobalException(ErrorCode.UNCHECKED_EMAIL_VALID);
        }
        else if(!signupDto.isNicknameValid()) {
            throw new GlobalException(ErrorCode.UNCHECKED_NICKNAME_VALID);
        }
        // 회원 가입시 기본 이미지 제공
        String imageUrl = "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg";

        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        User signupUser = signupDto.toEntity(signupDto, imageUrl);

        userRepository.save(signupUser);

    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);

        RefreshToken refreshToken = RefreshToken.builder()
                .loginUser(loginUser)
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);

        return LoginResponseDto.builder()
                .userId(loginUser.getUser().getUserId())
                .imageUrl(loginUser.getUser().getImageUrl())
                .email(loginUser.getUser().getUserEmail())
                .nickname(loginUser.getUser().getNickname())
                .username(loginUser.getUser().getUserName())
                .ageRange(loginUser.getUser().getUserAgeRange())
                .gender(loginUser.getUser().getUserGender())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
    @Transactional
    public ResponseDto<Void> emailCheck(EmailCheckRequestDto request) {
        String code = request.getCode();
        String email = request.getEmail();
        String authCode = redisUtils.getAuthCode(email);
        if(!redisUtils.existEmail(email)){
            log.info("레디스에 해당 이메일을 가지고 있다");
            throw new GlobalException(ErrorCode.EXPIRED_AUTHENTICATION_TIME);
        }
        if(!code.equals(authCode)){
            log.info("인증코드 불일치!");
            throw new GlobalException(ErrorCode.MISMATCHED_CODE);
        }
        redisUtils.deleteAuthCode(email);
        return ResponseDto.success("이메일 인증 성공");
    }

    public ResponseDto<Void> sendMail(SendEmailRequestDto request) {
        String email = request.getEmail();
        String authCode = createAuthCode();
        if(userRepository.existsByUserEmail(email)){
            throw new GlobalException(ErrorCode.USER_EMAIL_DUPLICATE);
        }else{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email); // 메일 수신자
            simpleMailMessage.setSubject("TravelMaker 인증번호 입니다"); // 메일 제목
            simpleMailMessage.setText("이메일 인증번호는 "+authCode+"입니다"); // 메일 본문
            simpleMailMessage.setFrom(sendEmail);
            mailSender.send(simpleMailMessage);
            redisUtils.saveCertificateNumber(email, authCode);
        }
        return ResponseDto.success("인증 메일 발송 완료");
    }

    private String createAuthCode() {
        // 0~9까지 중 6개를 추출해서 인증번호 만들기
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<6; i++){
            sb.append((int)(Math.random() * 10)); // 0~9까지
        }
        return sb.toString();
    }

    public ResponseDto<Void> nicknameCheck(NicknameRequest request) {
        String nickname = request.getNickname();
        if(userRepository.existsByNickname(nickname)){
            throw new GlobalException(ErrorCode.DUPLICATED_NICKNAME);
        }
        return ResponseDto.success("사용 가능한 닉네임 입니다");
    }
}
