package travelMaker.backend.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.time.LocalTime;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class TripPlanInfo {

    private Long tripPlanId;

    private String destinationName;

    private Integer wishCnt;

    private boolean wishJoin;

    private String address;

    private LocalTime arriveTime;

    private LocalTime leaveTime;

    private String region;

    private Double destinationX;

    private Double destinationY;

    public TripPlanInfo(TripPlan tripPlan) {
        this.tripPlanId = tripPlan.getTripPlanId();
        this.destinationName = tripPlan.getDestinationName();
        this.wishCnt = tripPlan.getWishCnt();
        this.wishJoin = tripPlan.isWishJoin();
        this.address = tripPlan.getAddress();
        this.arriveTime = tripPlan.getArriveTime();
        this.leaveTime = tripPlan.getLeaveTime();
        this.region = tripPlan.getRegion();
        this.destinationX= tripPlan.getDestinationX();
        this.destinationY= tripPlan.getDestinationY();
    }
}