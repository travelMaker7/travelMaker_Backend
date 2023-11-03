package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.service.MyPageService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "mypage controller")
@RequestMapping("/api/v1")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/profile")
    @Operation(summary = "본인 프로필 조회")
    ResponseDto<MyProfileDto> getMyProfile(@AuthenticationPrincipal LoginUser loginUser) {
        return ResponseDto.success("본인 프로필 조회 성공", myPageService.getMyProfile(loginUser));
    }

}
