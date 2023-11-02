package travelMaker.backend.schedule.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.schedule.dto.request.DailySchedule;
import travelMaker.backend.schedule.dto.request.DestinationDetail;
import travelMaker.backend.schedule.dto.request.ScheduleRegisterDto;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Rollback(value = false)
@Transactional
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("일정 등록")
    @Rollback(value = false)
    public void saveTest () throws Exception{

        // 세부 일정
        List<DestinationDetail> detailList  = new ArrayList<>();
        for(int i=0; i<3; i++){
            DestinationDetail details = DestinationDetail.builder()
                    .address("kkkksssss")
                    .region("제주도")
                    .destinationName("한라산")
                    .wishCnt(2)
                    .wishJoin(true)
                    .arriveTime(LocalTime.of(11,30))
                    .leaveTime(LocalTime.of(12, 30))
                    .destinationX(126.977)
                    .destinationY(37.5797)
                    .build();
            detailList.add(details);
        }

        // 일정
        List<DailySchedule> schedulelList  = new ArrayList<>();
        DailySchedule day1 = DailySchedule.builder()
                .scheduledDate(LocalDate.of(2032,10,10))
                .details(detailList)
                .build();
        DailySchedule day2 = DailySchedule.builder()
                .scheduledDate(LocalDate.of(2032,10,11))
                .details(detailList)
                .build();
        DailySchedule day3 = DailySchedule.builder()
                .scheduledDate(LocalDate.of(2032,10,12))
                .details(detailList)
                .build();

        schedulelList.add(day1);
        schedulelList.add(day2);
        schedulelList.add(day3);

        // 입력받은 등록일정 전체
        ScheduleRegisterDto registerDTO = ScheduleRegisterDto.builder()
                .scheduleName("hi trip")
                .scheduleDescription("with me")
                .startDate(LocalDate.of(2023, 10, 10))
                .finishDate(LocalDate.of(2023, 10, 12))
                .chatUrl("https://kakawo.com")
                .schedules(schedulelList)
                .build();

        //given

        //when
        scheduleService.register(registerDTO);

        //then

        Schedule schedule = scheduleRepository.findById(1L).orElseThrow();
        Assertions.assertThat(schedule.getScheduleName()).isEqualTo("hi trip");
    }

    @Test
    @DisplayName("일정 상세보기")
    public void viewDetails() throws Exception {

        //given
        Long scheduleId = 2l;

        //when
        ScheduleDetailsDto result = scheduleService.viewDetails(scheduleId);

        //then
        System.out.println(result);
    }

    @Test
    @DisplayName("일정 삭제")
    public void delete() throws Exception {

        //given
        Long scheduleId = 100l;

//        User user = User.builder()
//                .userId(100l)
//                .build();

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        User user = schedule.getUser();

        //when
        scheduleService.delete(scheduleId, new LoginUser(user));

        //then

    }
}