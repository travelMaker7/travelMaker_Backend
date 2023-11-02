package travelMaker.backend.schedule.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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
        Long userId = 5L;
        //when

        System.out.println(scheduleRepository.getAccompanyScheduleList(status, userId));
        //then

    }

}