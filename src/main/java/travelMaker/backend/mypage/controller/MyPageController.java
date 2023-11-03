package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;

import travelMaker.backend.mypage.dto.request.UpdateDescriptionDto;
import travelMaker.backend.mypage.dto.request.UpdateNicknameDto;
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


    private final MyPageService myPageService;

    @GetMapping("/mypage/profile/{targetUserId}")
    @Operation(summary = "타인 프로필 조회")
    public ResponseDto<UserProfileDto> showUserProfile(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal LoginUser loginUser
            ){

        return ResponseDto.success("타인 프로필 조회 성공", myPageService.getUserProfile(targetUserId, loginUser));
    }

    @PutMapping("/mypage/update/description")
    @Operation(summary = "프로필 소개글 수정")
    public ResponseDto<Void> updateProfileDescription(
            @Valid @RequestBody UpdateDescriptionDto updateDescriptionDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        myPageService.updateProfileDescription(updateDescriptionDto, loginUser);
        return ResponseDto.success("소개글 수정 완료" );
    }

    @PutMapping("/mypage/update/nickname")
    @Operation(summary = "프로필 닉네임 수정")
    public ResponseDto<Void> updateProfileNickname(
            @Valid @RequestBody UpdateNicknameDto updateNicknameDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        myPageService.updateProfileNickname(updateNicknameDto, loginUser);
        return ResponseDto.success("닉네임 수정 완료" );
    }
}
