package travelMaker.backend.tripPlan.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.tripPlan.dto.request.SearchRequest;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
class TripPlansRepositoryTest {
    @Autowired
    TripPlanRepository tripPlanRepository;


    @Test
    @DisplayName("지역으로 검색한 여행지를 가져온다")
    public void getRegionMakers() throws Exception{
        //given
        String region = "서울";
        //when
        List<TripPlan> byRegion = tripPlanRepository.findByRegion(region);
        //then
        for (TripPlan tripPlan : byRegion) {
            System.out.print(tripPlan.getDestinationName()+" ");
            System.out.print(tripPlan.getAddress());
            System.out.println();
        }

    }
    @Test
    void test() {
        SearchRequest request = new SearchRequest(LocalDate.of(2022, 11, 15),null,null, "male", 1, 6, null);
        List<TripPlan> tripPlans = tripPlanRepository.findTripPlansByConditions(request);
        System.out.println("Number of results: " + tripPlans.size());
        for(TripPlan tripPlan : tripPlans){
            System.out.println("tripPlan = " + tripPlan);
        }
    }
    @Test
    @DisplayName("order by 사용하기")
    public void test2() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        List<TripPlan> results = tripPlanRepository.findByDateDateIdInOrderByTripPlanIdAsc(ids); // in (date_id) order by tripPlan_id
        for (TripPlan result : results) {
            System.out.println(result);
        }
    }

}