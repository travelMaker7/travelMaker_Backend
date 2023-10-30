package travelMaker.backend.tripPlan.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

}