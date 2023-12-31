package travelMaker.backend.tripPlan.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import travelMaker.backend.chat.model.ChatRoom;
import travelMaker.backend.schedule.model.Date;

import java.time.LocalTime;

@SQLDelete(sql = "UPDATE trip_plan SET is_deleted = true WHERE trip_plan_id = ?")
@Where(clause = "is_deleted = false")
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

    private boolean isDeleted;

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
            Date date,
            ChatRoom chatRoom
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
    public void addStayTime(LocalTime arriveTime, LocalTime leaveTime){
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
    }

    public void addDestinationName(String destinationName){
        this.destinationName = destinationName;
    }
    public void addWishCnt(Integer wishCnt){
        this.wishCnt = wishCnt;
    }
    public void addWishJoin(boolean wishJoin){
        this.wishJoin = wishJoin;
    }
    public void addAddress(String address){
        this.address = address;
    }
    public void addArriveTime(LocalTime arriveTime){
        this.arriveTime = arriveTime;
    }
    public void addLeaveTime(LocalTime leaveTime){
        this.leaveTime = leaveTime;
    }
    public void addDestinationX(Double destinationX){
        this.destinationX = destinationX;
    }
    public void addDestinationY(Double destinationY){
        this.destinationY = destinationY;
    }

    public void addRegion(String region){
        this.region = region;
    }
}
