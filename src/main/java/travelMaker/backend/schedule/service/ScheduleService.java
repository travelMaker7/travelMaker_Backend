package travelMaker.backend.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.schedule.dto.request.DailySchedule;
import travelMaker.backend.schedule.dto.request.DestinationDetail;
import travelMaker.backend.schedule.dto.request.ScheduleRegisterDto;
import travelMaker.backend.schedule.dto.response.DetailsMarker;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.schedule.dto.response.TripPlans;
import travelMaker.backend.schedule.model.Date;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.DateRepository;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DateRepository dateRepository;
    private final TripPlanRepository tripPlanRepository;
    @Transactional
    public void register(ScheduleRegisterDto scheduleRegisterDTO, LoginUser loginUser) {

        Schedule Tripschedule = scheduleRegisterDTO.toScheduleEntity(loginUser.getUser());
        Schedule savedSchedule = scheduleRepository.save(Tripschedule);

        for (DailySchedule schedule : scheduleRegisterDTO.getSchedules()) {
            Date tripDate = Date.builder()
                    .scheduledDate(schedule.getScheduledDate())
                    .schedule(savedSchedule)
                    .build();
                dateRepository.save(tripDate);
            for (DestinationDetail detail : schedule.getDetails()) {
                LocalTime arriveTime = detail.getArriveTime();
                LocalTime leaveTime = detail.getLeaveTime();

                if(detail.getArriveTime() != null && detail.getLeaveTime() != null){
                    if(leaveTime.isBefore(arriveTime)){
                        throw new GlobalException(ErrorCode.SCHEDULE_TIME_OVERFLOW);
                    }
                }
                TripPlan trip = detail.toTripPlanEntity(tripDate);
                if(detail.isWishJoin()){
                    trip.addStayTime(detail.getArriveTime(), detail.getLeaveTime());
                }
                tripPlanRepository.save(trip);
            }
        }
    }


    @Transactional(readOnly = true)
    public ScheduleDetailsDto viewDetails(Long scheduleId) {

        List<DetailsMarker> markers = scheduleRepository.markers(scheduleId);
        log.info("markers ={} " , markers.size());

        List<TripPlans> tripPlans = scheduleRepository.tripPlans(scheduleId);
        log.info("tripPlans ={} " , tripPlans.size());

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        log.info("schedule ={} " , schedule);

        return ScheduleDetailsDto.builder()
                .scheduleId(scheduleId)
                .markers(markers) // 리스트
                .scheduleName(schedule.getScheduleName())
//                .startDate(schedule.getStartDate())
//                .finishDate(schedule.getFinishDate())
                .tripPlans(tripPlans) // 리스트
                .chatUrl(schedule.getChatUrl())
                .build();
    }

    public void delete(Long scheduleId, LoginUser loginUser) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        if (schedule.getUser().getUserId() == loginUser.getUser().getUserId()) {
            scheduleRepository.delete(schedule);
        } else {
            throw new GlobalException(ErrorCode.NOT_THE_PERSON_WHO_REGISTERED_THE_SCHEDULE);
        }
    }
}
