package travelMaker.backend.schedule.repository;

import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.dto.response.RegisteredDto;
import travelMaker.backend.schedule.dto.response.*;
import travelMaker.backend.user.login.LoginUser;

import java.util.List;

public interface ScheduleRepositoryCustom {

    List<DetailsMarker> markers(Long scheduleId);
    List<TripPlans> tripPlans(Long scheduleId);

    List<AccompanyTripPlans.AccompanyTripPlan> getAccompanyScheduleList(String status, Long userId);
    List<RegisteredDto.RegisterScheduleDto> getRegisterScheduleList(Long userId);


    List<DayByTripPlan> getScheduleAndTripPlanDetails(Long scheduleId) ;
}
