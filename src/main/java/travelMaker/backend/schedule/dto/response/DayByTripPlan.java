package travelMaker.backend.schedule.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class DayByTripPlan{

    private Long dateId;
    private LocalDate scheduledDate;
    private List<TripPlanDetails2> tripPlanDetails2s;
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class TripPlanDetails2{
        private Long tripPlanId;
        private String destinationName;
        private boolean overWish;
        private Long joinCnt;
        private Integer wishCnt;
        private boolean wishJoin;

        private String address;

        private LocalTime arriveTime;
        private LocalTime leaveTime;


    }

}