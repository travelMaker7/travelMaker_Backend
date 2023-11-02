package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.mypage.service.MyPageService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "mypage controller")
@RequestMapping("/api/v1")
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/mypage/profile/{targetUserId}")
    @Operation(summary = "타인 프로필 조회")
    public ResponseDto<UserProfileDto> showUserProfile(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal LoginUser loginUser
            ){

        return ResponseDto.success("타인 프로필 조회 성공", myPageService.getUserProfile(targetUserId, loginUser));
    }


}
