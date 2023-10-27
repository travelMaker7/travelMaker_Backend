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