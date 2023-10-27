package travelMaker.backend.tripPlan.model;

import jakarta.persistence.*;
import lombok.*;
import travelMaker.backend.schedule.model.Date;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TripPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripPlanId;
    private LocalTime arriveTime;
    private LocalTime leaveTime;
    private boolean wishJoin;
    private Integer wishCnt;
    private Integer joinCnt;
    private String destinationName;
    private String address;
    private Double destinationX;
    private Double destinationY;
    private String region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dateId")
    private Date date;

    @Builder
    public TripPlan(
            LocalTime arriveTime,
            LocalTime leaveTime,
            boolean wishJoin,
            Integer wishCnt,
            Integer joinCnt,
            String destinationName,
            String address,
            Double destinationX,
            Double destinationY,
            String region,
            Date date
    ) {
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
        this.wishJoin = wishJoin;
        this.wishCnt = wishCnt;
        this.joinCnt = joinCnt;
        this.destinationName = destinationName;
        this.address = address;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.region = region;
        this.date = date;
    }
}
