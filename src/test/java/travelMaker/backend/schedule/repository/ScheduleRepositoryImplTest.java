package travelMaker.backend.schedule.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.schedule.dto.response.TripPlanDetails;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class ScheduleRepositoryImplTest {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TripPlanRepository tripPlanRepository;


    @Test
    @DisplayName("상태별로 동행신청한 목록 가져오기")
    public void accompanyScheduleList() throws Exception{
        //given
        String status = "승인대기";
//        String status = "신청수락";
//        String status = "신청취소";
//        String status = "동행완료";
        Long userId = 5L;
        //when

        for (AccompanyTripPlans.AccompanyTripPlan accompanyTripPlan : scheduleRepository.getAccompanyScheduleList(status, userId)) {
            System.out.println(accompanyTripPlan);
        }

        //then

    }
//    @Test
//    @DisplayName("일정 상세보기 내에 tripPlanDetails 조회")
//    public void tripPlanDetails() {
//        List<TripPlanDetails> tripPlanDetails = scheduleRepository.tripPlanDetails(3L);
//        for (TripPlanDetails tripPlanDetail : tripPlanDetails) {
//            System.out.println("tripPlanDetail = " + tripPlanDetail);
//        }
//    }

}