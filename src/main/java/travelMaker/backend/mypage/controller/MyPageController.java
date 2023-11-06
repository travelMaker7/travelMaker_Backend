package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.mypage.dto.request.RegisterReviewDto;
import travelMaker.backend.mypage.dto.request.UpdateDescriptionDto;
import travelMaker.backend.mypage.dto.request.UpdateNicknameDto;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
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
    @GetMapping("/mypage/schedules/participating")
    @Operation(summary = "동행하는 일정 목록 가져오기")
    public ResponseDto<AccompanyTripPlans> getAccompanyTripPlanList(
            @Schema(description = "joinStatus 전송", example = "승인대기") @RequestParam String status,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        return ResponseDto.success("동행을 참여하는 목록 조회 성공", myPageService.getAccompanyListDependingOnStatus(status, loginUser));
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

  
    @GetMapping("/mypage/profile")
    @Operation(summary = "본인 프로필 조회")
    ResponseDto<MyProfileDto> getMyProfile(@AuthenticationPrincipal LoginUser loginUser) {
        return ResponseDto.success("본인 프로필 조회 성공", myPageService.getMyProfile(loginUser));
    }

    @PutMapping("/review/{scheduleId}")
    @Operation(summary = "리뷰 등록")
    ResponseDto<Void> registerReview(@Valid @RequestBody RegisterReviewDto registerReviewDto, @PathVariable Long scheduleId) {
        myPageService.registerReview(registerReviewDto, scheduleId);
        return ResponseDto.success("리뷰 등록 성공");
    }

}
