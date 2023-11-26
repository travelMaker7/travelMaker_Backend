package travelMaker.backend.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.user.dto.request.ReissueRequestDto;
import travelMaker.backend.user.dto.response.LoginResponseDto;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.service.UserService;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/auth/kakao")
    public ResponseDto<LoginResponseDto> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseDto.success("카카오 로그인 성공", userService.login(code));
    }
    @PostMapping("/reissue")
    public ResponseDto<LoginResponseDto> reissue(@Valid @RequestBody ReissueRequestDto request) {
        return ResponseDto.success("토큰 재발행을 성공했습니다.", userService.reissue(request));
    }

    @PostMapping("/logout")
    public ResponseDto<Void> logout(@AuthenticationPrincipal LoginUser loginUser) {
        String nickname = loginUser.getUser().getUserName();

        return ResponseDto.success(nickname + " 로그아웃 성공", null);
    }
}
