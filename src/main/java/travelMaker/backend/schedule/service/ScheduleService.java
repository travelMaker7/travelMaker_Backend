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
        LocalDate startDate = scheduleRegisterDTO.getStartDate();
        LocalDate finishDate = scheduleRegisterDTO.getFinishDate();

        if(finishDate.isBefore(startDate)){
            throw new GlobalException(ErrorCode.SCHEDULE_DATE_OVERFLOW);
        }

        Schedule Tripschedule = scheduleRegisterDTO.toScheduleEntity();
        Tripschedule.addUser(loginUser.getUser());

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

                // 1-1. 동행을 희망 하지 않는 경우 wishJoin = false, wishCnt = 0, arriveTime=00:00, leaveTime=00:00 default값을 준다?
                // 1-2. 동행을 희망 하지 않는 경우 wishJoin = false, {wishCnt = 0, arriveTime=00:00, leaveTime=00:00} 보내지 않는다
                // 도착 시간 & 출발 시간을 보내 주지 않았을 경우 -> nullPointerException 처리
                if(detail.getArriveTime() != null && detail.getLeaveTime() != null){

                    if(leaveTime.isBefore(arriveTime)){
                        throw new GlobalException(ErrorCode.SCHEDULE_TIME_OVERFLOW);
                    }
                }
                // wishJoin이 false일 경우 arriveTime, leaveTime은 저장할 필요가 없다
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
                .startDate(schedule.getStartDate())
                .finishDate(schedule.getFinishDate())
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
