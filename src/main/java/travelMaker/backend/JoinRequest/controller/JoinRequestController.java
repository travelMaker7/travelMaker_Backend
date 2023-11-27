package travelMaker.backend.JoinRequest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.JoinRequest.dto.request.HostJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;
import travelMaker.backend.JoinRequest.service.JoinRequestService;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;
import travelMaker.backend.user.login.LoginUser;

import static travelMaker.backend.common.dto.ResponseDto.success;
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "joinRequest Controller")
@RequestMapping("/api/v1")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping("/accompany/guest")
    @Operation(summary = "동행 신청")
    ResponseDto<Void> AccompanyRequest(@Valid @RequestBody GuestJoinRequestDto guestJoinRequestDto, @AuthenticationPrincipal LoginUser loginUser) {
        log.info("AccompanyRequest");
        joinRequestService.guestJoinRequest(guestJoinRequestDto, loginUser);
        return success("joinStatus 업데이트 성공: 승인대기");
    }

    @DeleteMapping("/accompany/guest/{tripPlanId}")
    @Operation(summary = "동행 신청 취소")
    ResponseDto<Void> AccompanyCancel(@PathVariable Long tripPlanId, @AuthenticationPrincipal LoginUser loginUser) {
        log.info("AccompanyCancel");
        joinRequestService.guestJoinCancel(tripPlanId, loginUser);
        return success("joinStatus 업데이트 성공: 신청취소");
    }

    @PostMapping("/accompany/host")
    @Operation(summary = "동행 신청수락/신청거절")
    ResponseDto<Void> AccompanyRequestAcceptOrReject(@Valid @RequestBody HostJoinRequestDto hostJoinRequestDto) {
        log.info("AccompanyRequestAcceptOrReject");
        joinRequestService.hostJoinRequest(hostJoinRequestDto);
        return success("joinStatus 업데이트 성공: 신청수락/신청거절");
    }

    @GetMapping("/accompany")
    @Operation(summary = "동행 신청 알림")
    ResponseDto<NotificationsDto> JoinRequestNotification(@AuthenticationPrincipal LoginUser loginUser) {
        log.info("JoinRequestNotification");
        log.info("userId : {}",loginUser.getUser().getUserId());
        return ResponseDto.success("동행 신청 알림 조회 성공", joinRequestService.joinRequestNotifications(loginUser));
    }

}