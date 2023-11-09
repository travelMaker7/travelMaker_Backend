package travelMaker.backend.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.schedule.service.ScheduleService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "schedule controller")
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

//    @PostMapping("/schedule")
//    @Operation(summary = "여행 일정 등록")
//    ResponseDto<Void> scheduleRegister(
//            @Valid @RequestBody ScheduleRegisterDto scheduleRegisterDTO,
//            @AuthenticationPrincipal LoginUser loginUser
//    ){
//        scheduleService.register(scheduleRegisterDTO, loginUser);
//        return ResponseDto.success("일정 등록 성공");
//    }

    @GetMapping("/schedule/detail/{scheduleId}")
    @Operation(summary = "일정 상세보기")
    public ResponseDto<ScheduleDetailsDto> scheduleDetails(@PathVariable Long scheduleId) {
        return ResponseDto.success("일정 상세보기 조회 성공", scheduleService.viewDetails(scheduleId));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 삭제")
    ResponseDto<Void> scheduleDelete(@PathVariable Long scheduleId, @AuthenticationPrincipal LoginUser loginUser) {
        scheduleService.delete(scheduleId, loginUser);
        return ResponseDto.success("일정 삭제 성공");
    }

}
