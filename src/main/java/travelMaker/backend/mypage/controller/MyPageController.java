package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.mypage.dto.request.UpdateProfileDto;
import travelMaker.backend.mypage.service.MyPageService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "mypage controller")
@RequestMapping("/api/v1")
public class MyPageController {

    private final MyPageService myPageService;

    @PutMapping("/mypage/profile")
    @Operation(summary = "프로필 수정(소개글 or 닉네임)")
    public ResponseDto<Void> updateProfile(
            @RequestBody UpdateProfileDto updateProfileDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        myPageService.updateProfile(updateProfileDto, loginUser);
        return ResponseDto.success("소개글 or 닉네임 수정 완료" );
    }
}
