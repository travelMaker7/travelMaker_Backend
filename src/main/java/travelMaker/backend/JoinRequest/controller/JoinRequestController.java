package travelMaker.backend.JoinRequest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelMaker.backend.JoinRequest.service.JoinRequestService;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;

@RestController
@RequiredArgsConstructor
@Tag(name = "joinRequest Controller")
@RequestMapping("/api/v1")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping("/accompany/guest")
    @Operation(summary = "동행 신청/신청 취소")
    ResponseDto<Void> AccompanyRequestOrCancel(@Valid @RequestBody GuestJoinRequestDto guestJoinRequestDto) {
        joinRequestService.guestJoinRequest(guestJoinRequestDto);
        return ResponseDto.success("joinStatus 업데이트 성공: 승인대기/신청취소");
    }
}
