package travelMaker.backend.mypage.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccompanyTripPlans {

    private List<AccompanyTripPlan> schedules;

    @Getter
    @NoArgsConstructor
    public static class AccompanyTripPlan{
        private Long scheduleId;
        private String scheduleName;
        private LocalDate scheduledDate;
        private LocalTime arriveTime;
        private LocalTime leaveTime;
        private String nickname;
        private String region;

        public AccompanyTripPlan(Long scheduleId, String scheduleName, LocalDate scheduledDate, LocalTime arriveTime, LocalTime leaveTime, String nickname, String region) {
            this.scheduleId = scheduleId;
            this.scheduleName = scheduleName;
            this.scheduledDate = scheduledDate;
            this.arriveTime = arriveTime;
            this.leaveTime = leaveTime;
            this.nickname = nickname;
            this.region = region;
        }
    }
}

