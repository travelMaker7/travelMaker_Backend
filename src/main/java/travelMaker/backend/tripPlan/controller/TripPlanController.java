package travelMaker.backend.tripPlan.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.service.TripPlanService;

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
    ResponseDto<SearchRegionDto> searchRegion(@PathVariable String region, String destinationName){
        return ResponseDto.success("여행지 리스트 조회 성공", tripPlanService.searchRegion(region, destinationName));
    }


}
