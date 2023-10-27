package travelMaker.backend.tripPlan.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
