package travelMaker.backend.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.chat.service.ChatRoomService;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.schedule.dto.request.DailySchedule;
import travelMaker.backend.schedule.dto.request.DestinationDetail;
import travelMaker.backend.schedule.dto.request.ScheduleRegisterDto;
import travelMaker.backend.schedule.dto.response.*;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DateRepository dateRepository;
    private final TripPlanRepository tripPlanRepository;
    private final ChatRoomService chatRoomService;

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
                TripPlan savedTripPlan = tripPlanRepository.save(trip);

                Long tripPlanId = savedTripPlan.getTripPlanId();

                if(savedTripPlan.isWishJoin()){
                    chatRoomService.createGroupChatRoom(tripPlanId,savedSchedule.getScheduleName(),loginUser.getUser());
                }
            }
        }
    }


    @Transactional(readOnly = true)
    public ScheduleDetailsDto viewDetails(Long scheduleId) {

        List<DetailsMarker> markers = scheduleRepository.markers(scheduleId);
        log.info("markers ={} ", markers.size());

        List<TripPlans> tripPlans = scheduleRepository.tripPlans(scheduleId);

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        log.info("schedule ={} ", schedule);

        return ScheduleDetailsDto.builder()
                .scheduleId(scheduleId)
                .markers(markers) // 리스트
                .scheduleName(schedule.getScheduleName())
                .tripPlans(tripPlans) // 리스트
                .scheduleDescription(schedule.getScheduleDescription())
                .chatUrl(schedule.getChatUrl())
                .build();
    }


    @Transactional
    public void delete(Long scheduleId, LoginUser loginUser) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        if (schedule.getUser().getUserId() == loginUser.getUser().getUserId()) {
            scheduleRepository.delete(schedule);
        } else {
            throw new GlobalException(ErrorCode.NOT_THE_PERSON_WHO_REGISTERED_THE_SCHEDULE);
        }
    }

    @Transactional(readOnly = true)
    public ScheduleInfoDto getScheduleInfoAndDetails(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));

        List<DateAndTripPlanInfo> dayByTripPlansResults = scheduleRepository.getTripPlanDetailsBeforeChange(scheduleId);

        // 1. scheduledDate별로 그룹화 하기
        Map<LocalDate, List<TripPlanInfo>> groupedByDate = dayByTripPlansResults.stream()
                .collect(Collectors.groupingBy(
                        DateAndTripPlanInfo::getScheduledDate,
                        Collectors.mapping(DateAndTripPlanInfo::getTripPlanInfo, Collectors.toList())
                ));

        // 2. 그룹화된 결과를 바탕으로 새로운 DayByTripPlan 객체 리스트를 생성
        List<DayByTripPlans> dayByTripPlans = groupedByDate.entrySet().stream()
                .map(entry -> new DayByTripPlans(
                        entry.getKey(), // scheduledDate
                        entry.getValue() // 해당 날짜에 해당하는 TripPlanDetail 리스트
                ))
                .toList();
        return ScheduleInfoDto.from(dayByTripPlans, schedule);

    }
}
