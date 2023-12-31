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
import travelMaker.backend.mypage.dto.response.*;
import travelMaker.backend.mypage.service.MyPageService;
import travelMaker.backend.user.login.LoginUser;

import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;

import static travelMaker.backend.common.dto.ResponseDto.success;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "mypage controller")
@RequestMapping("/api/v1")
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/mypage/bookmark")
    @Operation(summary = "북마크 목록 조회")
    public ResponseDto<BookMarkPlansDto> getBookMarkList(@AuthenticationPrincipal LoginUser loginUser){
        log.info("getBookMarkList");
        return ResponseDto.success("북마크 목록 조회 성공", myPageService.getBookMarkList(loginUser));
    }

    @GetMapping("/mypage/profile/{targetUserId}")
    @Operation(summary = "타인 프로필 조회")
    public ResponseDto<UserProfileDto> showUserProfile(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        log.info("showUserProfile");
        return ResponseDto.success("타인 프로필 조회 성공", myPageService.getUserProfile(targetUserId, loginUser));
    }
    @GetMapping("/mypage/schedules/participating")
    @Operation(summary = "동행하는 일정 목록 가져오기")
    public ResponseDto<AccompanyTripPlans> getAccompanyTripPlanList(
            @Schema(description = "joinStatus 전송", example = "승인대기") @RequestParam String status,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        log.info("getAccompanyTripPlanList");
        return ResponseDto.success("동행을 참여하는 목록 조회 성공", myPageService.getAccompanyListDependingOnStatus(status, loginUser));
    }

    @PutMapping("/mypage/update/description")
    @Operation(summary = "프로필 소개글 수정")
    public ResponseDto<Void> updateProfileDescription(
            @Valid @RequestBody UpdateDescriptionDto updateDescriptionDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        log.info("updateProfileDescription");
        myPageService.updateProfileDescription(updateDescriptionDto, loginUser);
        return ResponseDto.success("소개글 수정 완료" );

    }

    @PutMapping("/mypage/update/nickname")
    @Operation(summary = "프로필 닉네임 수정")
    public ResponseDto<Void> updateProfileNickname(
            @Valid @RequestBody UpdateNicknameDto updateNicknameDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        log.info("updateProfileNickname");
        myPageService.updateProfileNickname(updateNicknameDto, loginUser);
        return ResponseDto.success("닉네임 수정 완료" );
    }


    @GetMapping("/mypage/profile")
    @Operation(summary = "본인 프로필 조회")
    ResponseDto<MyProfileDto> getMyProfile(@AuthenticationPrincipal LoginUser loginUser) {
        log.info("getMyProfile");
        return ResponseDto.success("본인 프로필 조회 성공", myPageService.getMyProfile(loginUser));
    }

    @DeleteMapping("/mypage/profile")
    @Operation(summary = "회원 탈퇴")
    ResponseDto<Void> deleteUserByName(@AuthenticationPrincipal LoginUser loginUser){
        log.info("deleteUserByName");
        myPageService.deleteUserByUserId(loginUser);
        return ResponseDto.success("회원 탈퇴 성공",null);
    }
    @GetMapping("/mypage/schedules/registered")
    @Operation(summary = "등록한 일정 조회")
    ResponseDto<RegisteredScheduleListDto> getRegisterSchedule(@AuthenticationPrincipal LoginUser loginUser){
        log.info("getRegisterSchedule");
        return ResponseDto.success("등록한 일정 조회 성공",myPageService.getRegisterScheduleList(loginUser));
    }

    @PostMapping("/review")
    @Operation(summary = "리뷰 등록")
    ResponseDto<Void> registerReview(@Valid @RequestBody RegisterReviewDto registerReviewDto, @AuthenticationPrincipal LoginUser loginUser) {
        myPageService.registerReview(registerReviewDto, loginUser);
        return ResponseDto.success("리뷰 등록 성공");
    }


    @PostMapping("/mypage/bookmark/{scheduleId}")
    @Operation(summary = "북마크 등록")
    ResponseDto<Void> bookMarkRegister(@PathVariable Long scheduleId, @AuthenticationPrincipal LoginUser loginUser) {
        log.info("bookMarkRegister");
        myPageService.bookMarkRegister(scheduleId, loginUser);
        return success("북마크 등록");
    }
    @DeleteMapping("/mypage/bookmark/{bookmarkId}")
    @Operation(summary = "북마크 취소")
    ResponseDto<Void> bookMarkDelete(@PathVariable Long bookmarkId, @AuthenticationPrincipal LoginUser loginUser) {
        log.info("bookMarkDelete");
        myPageService.bookMarkDelete(bookmarkId, loginUser);
        return success("북마크 취소");
    }

    @GetMapping("/mypage/schedules/{scheduleId}")
    @Operation(summary = "참여하는 일정의 동행 인원 조회")
    ResponseDto<JoinUsers> getJoinUserList(@PathVariable Long scheduleId, Long tripPlanId){
        log.info("getJoinUserList");
        return ResponseDto.success("참여일정 동행인원 조회 성공", myPageService.getJoinUserList(scheduleId, tripPlanId));
    }

}
