package travelMaker.backend.tripPlan.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.tripPlan.dto.request.SearchRequest;
import travelMaker.backend.tripPlan.dto.request.UpdateTripPlanDto;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.dto.response.MarkerDto;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.tripPlan.service.TripPlanService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "tripPlan controller")
@RequestMapping("/api/v1")

public class TripPlanController {

    private final TripPlanService tripPlanService;
    private final TripPlanRepository tripPlanRepository;


    @GetMapping("/map/{region}")
    @Operation(summary = "지역별 마커들 조회")
    ResponseDto<MarkerDto> scheduleAllMaker(@PathVariable String region){
        return ResponseDto.success("마커들 가져오기 성공", tripPlanService.getAllMaker(region));
    }

//    @GetMapping("/trip/{region}")
//    @Operation(summary = "여행지 리스트 조회")
//    ResponseDto<SearchRegionDto> searchRegion(
//            @PathVariable String region,
//            @RequestParam String destinationX,
//            @RequestParam String destinationY
//    ){
//        return ResponseDto.success("여행지 리스트 조회 성공", tripPlanService.searchTripPlans(region, destinationX, destinationY));
//    }
    @GetMapping("/map")
    @Operation(summary = "검색된 마커들 조회")
    ResponseDto<MarkerDto> scheduleAllMaker(@ModelAttribute SearchRequest searchRequest){
        return ResponseDto.success("마커들 가져오기 성공", tripPlanService.searchedMaker(searchRequest));
    }
    @GetMapping("/trip/search")
    @Operation(summary = "검색결과 여행지 리스트 조회")
    ResponseDto<SearchRegionDto> searchTripPlan(
            Pageable pageable,
            @ModelAttribute SearchRequest request,
            @RequestParam String destinationX,
            @RequestParam String destinationY
    ){
        log.info("searchTripPlan");
        return ResponseDto.success("검색된 리스트 조회 성공", tripPlanService.searchRegionDto(pageable, request, destinationX, destinationY));
    }

    @PutMapping("/schedule/{scheduleId}/details/{tripPlanId}")
    @Operation(summary = "여행지 수정")
    public ResponseDto<Void> updateTripPlan(
            @PathVariable Long scheduleId,
            @PathVariable Long tripPlanId,
            @RequestBody UpdateTripPlanDto updateTripPlanDto,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        log.info("updateTripPlan");
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
        log.info("deleteTripPlan");
        tripPlanService.deleteTripPlan(scheduleId, tripPlanId, loginUser);
        return ResponseDto.success("여행지 삭제 성공");
    }


}
