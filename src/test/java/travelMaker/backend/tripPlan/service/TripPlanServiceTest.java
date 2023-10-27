package travelMaker.backend.tripPlan.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;

@SpringBootTest
class TripPlanServiceTest {
    @Autowired
    TripPlanService tripPlanService;


    @Test
    @DisplayName("여행지 리스트 조회")
    public void searchTripPlan() throws Exception{
        //given
        String region = "서울";
        String destination = "더현대";
        //when

        SearchRegionDto searchRegionDto = tripPlanService.searchRegion(region, destination);
        //then

        System.out.println(searchRegionDto);
    }

}