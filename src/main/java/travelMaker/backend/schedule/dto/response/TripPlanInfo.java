package travelMaker.backend.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class TripPlanInfo {

    private Long tripPlanId;

    private String destinationName;

//    private boolean overWish;

//    private Long joinCnt;

    private Integer wishCnt;

    private boolean wishJoin;

    private String address;

    private LocalTime arriveTime;

    private LocalTime leaveTime;

    private String region;

    private Double destinationX;

    private Double destinationY;


}