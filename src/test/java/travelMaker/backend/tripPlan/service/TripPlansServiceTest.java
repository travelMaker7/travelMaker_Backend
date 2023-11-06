package travelMaker.backend.tripPlan.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripPlansServiceTest {
    @Autowired
    TripPlanService tripPlanService;


    @Test
    @DisplayName("여행지 리스트 조회")
    public void searchTripPlan() throws Exception{
        //given
        String region = "서울";
//        String region = "제주도";
        String destination = "더현대";

        String x = "131.1";
        String y = "35.1";
        //when

        SearchRegionDto searchRegionDto = tripPlanService.searchTripPlans(region, x, y);
        //then

        System.out.println(searchRegionDto);
    }
    @Test
      @DisplayName("마커들 가져오기")
      public void  getAllMaker() throws Exception{
          //given
          String region = "서울";
          //when
          MakerDto allMaker = tripPlanService.getAllMaker(region);
          //then
          System.out.println(allMaker);
      }

}