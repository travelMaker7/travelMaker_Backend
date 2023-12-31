package travelMaker.backend.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.user.dto.request.*;
import travelMaker.backend.user.dto.response.LoginResponseDto;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.service.UserService;
@RestController
@RequiredArgsConstructor
@Tag(name = "User controller")
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/auth/kakao")
    @Operation(summary = "카카오 로그인")
    public ResponseDto<LoginResponseDto> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseDto.success("카카오 로그인 성공", userService.kakaoLogin(code));
    }
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발행")
    public ResponseDto<LoginResponseDto> reissue(@Valid @RequestBody ReissueRequestDto request) {
        return ResponseDto.success("토큰 재발행을 성공했습니다.", userService.reissue(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseDto<Void> logout(@AuthenticationPrincipal LoginUser loginUser) {
        String nickname = loginUser.getUser().getUserName();

        return ResponseDto.success(nickname + " 로그아웃 성공", null);
    }
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseDto<Void> signup(@RequestBody @Valid SignupRequestDto signUpRequestDto){
        userService.signup(signUpRequestDto);
        return ResponseDto.success("회원가입 성공");
    }
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseDto.success("로그인 성공", userService.login(loginRequestDto));
    }

}
