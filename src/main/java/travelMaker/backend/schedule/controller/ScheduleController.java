package travelMaker.backend.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.schedule.dto.request.ScheduleRegisterDto;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.schedule.dto.response.ScheduleInfoDto;
import travelMaker.backend.schedule.service.ScheduleService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "schedule controller")
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    @Operation(summary = "여행 일정 등록")
    ResponseDto<Void> scheduleRegister(
            @Valid @RequestBody ScheduleRegisterDto scheduleRegisterDTO,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        log.info("scheduleRegister");
        scheduleService.register(scheduleRegisterDTO, loginUser);
        return ResponseDto.success("일정 등록 성공");
    }

    @GetMapping("/schedule/detail/{scheduleId}")
    @Operation(summary = "일정 상세보기")
    public ResponseDto<ScheduleDetailsDto> scheduleDetails(@PathVariable Long scheduleId) {
        log.info("scheduleDetails");
        return ResponseDto.success("일정 상세보기 조회 성공", scheduleService.viewDetails(scheduleId));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 삭제")
    ResponseDto<Void> scheduleDelete(@PathVariable Long scheduleId, @AuthenticationPrincipal LoginUser loginUser) {
        log.info("scheduleDelete");
        scheduleService.delete(scheduleId, loginUser);
        return ResponseDto.success("일정 삭제 성공");
    }

    @GetMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 조회(수정하기 위해 보여지는 데이터)")
    public ResponseDto<ScheduleInfoDto> scheduleBeforeUpdate(@PathVariable Long scheduleId){
        log.info("scheduleBeforeUpdate");
        return ResponseDto.success("일정 조회 성공", scheduleService.getScheduleInfoAndDetails(scheduleId));
    }
    @GetMapping("/scheduletest/{scheduleId}")
    @Operation(summary = "일정 조회(수정하기 위해 보여지는 데이터 - spring data JPA로만 사용)")
    public ResponseDto<ScheduleInfoDto> scheduleBeforeUpdate2(@PathVariable Long scheduleId){
        log.info("test용입니다~");
        return ResponseDto.success("일정 조회 성공", scheduleService.getScheduleInfoAndDetail2(scheduleId));
    }

}
