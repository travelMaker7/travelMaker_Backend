package travelMaker.backend.tripPlan.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.schedule.dto.request.DestinationDetail;
import travelMaker.backend.schedule.dto.response.TripPlanDetails;
import travelMaker.backend.tripPlan.dto.request.UpdateTripPlanDto;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.service.TripPlanService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "tripPlan controller")
@RequestMapping("/api/v1")

public class TripPlanController {

    private final TripPlanService tripPlanService;


    @GetMapping("/map/{region}")
    @Operation(summary = "지역별 마커들 조회")
    ResponseDto<MakerDto> scheduleAllMaker(@PathVariable String region){
        return ResponseDto.success("마커들 가져오기 성공", tripPlanService.getAllMaker(region));
    }

    @GetMapping("/trip/{region}")
    @Operation(summary = "여행지 리스트 조회")
    ResponseDto<SearchRegionDto> searchRegion(
            @PathVariable String region,
            @RequestParam String destinationX,
            @RequestParam String destinationY
    ){
        return ResponseDto.success("여행지 리스트 조회 성공", tripPlanService.searchTripPlans(region, destinationX, destinationY));
    }

    @PutMapping("/schedule/{scheduleId}/details/{tripPlanId}")
    @Operation(summary = "여행지 수정")
    public ResponseDto<Void> updateTripPlan(
            @PathVariable Long scheduleId,
            @PathVariable Long tripPlanId,
            @RequestBody UpdateTripPlanDto updateTripPlanDto,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        tripPlanService.updateTripPlan(scheduleId, tripPlanId, updateTripPlanDto, loginUser);
        return ResponseDto.success("여행지 수정 성공");
    }
    @DeleteMapping("/schedule/{scheduleId}/details/{tripPlanId}")
    @Operation(summary = "여행지 삭제")
    public ResponseDto<Void> deleteTripPlan(
            @PathVariable Long scheduleId,
            @PathVariable Long tripPlanId,
            @AuthenticationPrincipal LoginUser loginUser

    ){
        tripPlanService.deleteTripPlan(scheduleId, tripPlanId, loginUser);
        return ResponseDto.success("여행지 삭제 성공");
    }


}
