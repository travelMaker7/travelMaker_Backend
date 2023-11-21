package travelMaker.backend.tripPlan.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.schedule.dto.request.DestinationDetail;
import travelMaker.backend.tripPlan.dto.request.SearchRequest;
import travelMaker.backend.tripPlan.dto.request.UpdateTripPlanDto;
import travelMaker.backend.tripPlan.dto.response.MakerDto;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripPlansServiceTest {
    @Autowired
    TripPlanService tripPlanService;

    @Autowired
    TripPlanRepository tripPlanRepository;
    @Test
    void test() throws Exception{
        SearchRequest request = new SearchRequest(LocalDate.of(2022, 11, 15),null,"30~39", "male", 1, 6, "강원도");
        MakerDto allMaker = tripPlanService.searchedMaker(request);
        for (MakerDto.Maker maker : allMaker.getMakers()){
            System.out.println("maker = " + maker.getDestinationName());
        }
    }

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

//      @Test
//      @DisplayName("여행지 단건 수정")
//      public void updateTripPlan() throws Exception{
//          //given
//          Long tripPlanId = 1L;
//          Long scheduleId = 1L;
//          UpdateTripPlanDto 소싹마을 = UpdateTripPlanDto.builder()
//                  .destinationName("소싹마을")
//                  .build();
//          //when
//          tripPlanService.updateTripPlan(tripPlanId, scheduleId, 소싹마을, new LoginUser(User.builder().userId(1L).build()));
//          //then
//          TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).get();
//          Assertions.assertThat(tripPlan.getDestinationName()).isEqualTo("소싹마을");
//
//      }

}