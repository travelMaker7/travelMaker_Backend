package travelMaker.backend.schedule.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.schedule.dto.response.DayByTripPlan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class ScheduleRepositoryImplTest {
    @Autowired
    ScheduleRepository scheduleRepository;


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

    @Test
    @DisplayName("여행 상세 보기 - day와 tripPlan보여주기 : 미완성 쿼리임!!! ")
    public void  showTripPlanDetails() throws Exception{
        //주의할점! tripPlanId가 없을 떄 date.id, date.scheduledDate도 없다? - 다시한번 확인해보기
        //given
        Long scheduleId = 2L;

        //when
//        List<DayByTripPlan> result = scheduleRepository.getScheduleAndTripPlanDetails(scheduleId);
        List<DayByTripPlan> result = scheduleRepository.getScheduleAndTripPlanDetails(scheduleId);

        //then
//        System.out.println(result); // 918ms 걸림 개오바~
        for (DayByTripPlan tripPlanDetails2 : result) {
            System.out.println(tripPlanDetails2.getScheduledDate());
            System.out.println(tripPlanDetails2.getDateId());
            for (DayByTripPlan.TripPlanDetails2 planDetails2 : tripPlanDetails2.getTripPlanDetails2s()) {
                System.out.println(planDetails2);
            }
        }
    }

}