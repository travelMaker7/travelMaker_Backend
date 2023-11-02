package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.service.MyPageService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "mypage controller")
@RequestMapping("/api/v1")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/schedules/participating")
    @Operation(summary = "동행하는 일정 목록 가져오기")
    public ResponseDto<AccompanyTripPlans> getAccompanyTripPlanList(
            @Schema(description = "joinStatus 전송", example = "승인대기") @RequestParam String status,
            @AuthenticationPrincipal LoginUser loginUser
            ){
        return ResponseDto.success("동행을 참여하는 목록 조회 성공", myPageService.getAccompanyListDependingOnStatus(status, loginUser));
    }

}
