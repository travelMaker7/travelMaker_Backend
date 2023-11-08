package travelMaker.backend.tripPlan.model;

import jakarta.persistence.*;
import lombok.*;
import travelMaker.backend.JoinRequest.model.JoinRequest;
import travelMaker.backend.schedule.model.Date;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TripPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripPlanId;
    private LocalTime arriveTime;
    private LocalTime leaveTime;
    @Column(nullable = false)
    private boolean wishJoin;
    private Integer wishCnt;


    @Column(nullable = false)
    private String destinationName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double destinationX;

    @Column(nullable = false)
    private Double destinationY;

    @Column(nullable = false)
    private String region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dateId")
    private Date date;

    @OneToMany(mappedBy = "tripPlan", cascade = CascadeType.ALL)
    private List<JoinRequest> joinRequests = new ArrayList<>();


    @PrePersist // 엔티티가 처음 저장될 때만 호출
    public void prePersist() {
        if (wishCnt == null) {
            wishCnt = 0;
        }
    }
    @Builder
    public TripPlan(
            LocalTime arriveTime,
            LocalTime leaveTime,
            boolean wishJoin,
            Integer wishCnt,
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
        this.destinationName = destinationName;
        this.address = address;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.region = region;
        this.date = date;
    }

//    public void increaseJoinCnt(Integer joinCnt) {
//        this.joinCnt += 1;
//    }
//    public void decreaseJoinCnt(Integer joinCnt) {
//        this.joinCnt -= 1;
//    }

    public void addStayTime(LocalTime arriveTime, LocalTime leaveTime){
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
    }
}
