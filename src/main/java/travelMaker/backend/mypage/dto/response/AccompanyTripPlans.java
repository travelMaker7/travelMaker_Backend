package travelMaker.backend.mypage.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccompanyTripPlans {

    private List<AccompanyTripPlan> schedules;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class AccompanyTripPlan{
        private Long scheduleId;
        private Long tripPlanId;
        private String scheduleName;
        private LocalDate scheduledDate;
        private LocalTime arriveTime;
        private LocalTime leaveTime;
        private String nickname;
        private String region;
        private String destinationName;

        public AccompanyTripPlan(Long scheduleId, Long tripPlanId, String scheduleName, LocalDate scheduledDate, LocalTime arriveTime, LocalTime leaveTime, String nickname, String region, String destinationName) {
            this.scheduleId = scheduleId;
            this.tripPlanId = tripPlanId;
            this.scheduleName = scheduleName;
            this.scheduledDate = scheduledDate;
            this.arriveTime = arriveTime;
            this.leaveTime = leaveTime;
            this.nickname = nickname;
            this.region = region;
            this.destinationName = destinationName;
        }
    }
}

