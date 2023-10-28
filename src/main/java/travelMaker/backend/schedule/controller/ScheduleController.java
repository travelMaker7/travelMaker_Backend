package travelMaker.backend.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.schedule.dto.request.ScheduleRegisterDto;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.schedule.service.ScheduleService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "schedule controller")
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    @Operation(summary = "여행 일정 등록")
    ResponseDto<Void> scheduleRegister(@Valid @RequestBody ScheduleRegisterDto scheduleRegisterDTO){
        scheduleService.register(scheduleRegisterDTO);
        return ResponseDto.success("일정 등록 성공");
    }

    @GetMapping("/schedule/detail/{scheduleId}")
    @Operation(summary = "일정 상세보기")
    ResponseDto<ScheduleDetailsDto> scheduleDetails(@PathVariable Long scheduleId) {
        return ResponseDto.success("일정 상세보기 조회 성공", scheduleService.viewDetails(scheduleId));
    }


}
