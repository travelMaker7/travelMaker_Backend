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
import travelMaker.backend.schedule.model.Date;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.DateRepository;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DateRepository dateRepository;
    private final TripPlanRepository tripPlanRepository;
    @Transactional
    public void register(ScheduleRegisterDto scheduleRegisterDTO) {
        LocalDate startDate = scheduleRegisterDTO.getStartDate();
        LocalDate finishDate = scheduleRegisterDTO.getFinishDate();
        if(finishDate.isBefore(startDate)){
            throw new GlobalException(ErrorCode.SCHEDULE_DATE_OVERFLOW);
        }

        Schedule Tripschedule = scheduleRegisterDTO.toScheduleEntity();
        Schedule savedSchedule = scheduleRepository.save(Tripschedule);

        for (DailySchedule schedule : scheduleRegisterDTO.getSchedules()) {

            Date tripDate = Date.builder()
                    .scheduleDate(schedule.getScheduledDate())
                    .schedule(savedSchedule)
                    .build();
            dateRepository.save(tripDate);

            for (DestinationDetail detail : schedule.getDetails()) {
                LocalTime arriveTime = detail.getArriveTime();
                LocalTime leaveTime = detail.getLeaveTime();
                if(leaveTime.isBefore(arriveTime)){
                    throw new GlobalException(ErrorCode.SCHEDULE_TIME_OVERFLOW);
                }
                TripPlan trip = detail.toTripPlanEntity(tripDate);
                tripPlanRepository.save(trip);
            }
        }
    }


}
