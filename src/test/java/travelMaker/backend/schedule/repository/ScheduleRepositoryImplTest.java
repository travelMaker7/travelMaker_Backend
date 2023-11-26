package travelMaker.backend.schedule.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.schedule.dto.response.DateAndTripPlanInfo;
import travelMaker.backend.schedule.dto.response.TripPlans;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;

import java.util.List;

@SpringBootTest
@Rollback(value = false)
class ScheduleRepositoryImplTest {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TripPlanRepository tripPlanRepository;


    @Test
    @DisplayName("데이터 조회")
    public void details() throws Exception{
        //given
        List<TripPlans> tripPlans = scheduleRepository.tripPlans(3L);
        //when
        for (TripPlans tripPlan : tripPlans) {
            System.out.println("결과 : "+tripPlan);
        }
        //then

    }

    @Test
    @DisplayName("동행 신청한 목록 가져오기")
    public void accompanyScheduleList() throws Exception{
        //given
        String status = "승인대기";
//        String status = "신청수락";
//        String status = "신청취소";
//        String status = "동행완료";
        Long userId = 2L;
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

    @Test
    @DisplayName("여행 상세 보기 - day와 tripPlan보여주기 : 미완성 쿼리임!!! ")
    public void  showTripPlanDetails() throws Exception{
        //주의할점! tripPlanId가 없을 떄 date.id, date.scheduledDate도 없다? - 다시한번 확인해보기
        //given
//        Long scheduleId = 2L; -> 데이터 없음
        Long scheduleId = 1L;

        //when
        List<DateAndTripPlanInfo> result = scheduleRepository.getTripPlanDetailsBeforeChange(scheduleId);

        //then
        for (DateAndTripPlanInfo tripPlanDetail : result) {
            System.out.println(tripPlanDetail);
        }
    }



}