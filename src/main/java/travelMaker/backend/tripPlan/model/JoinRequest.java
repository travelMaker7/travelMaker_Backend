package travelMaker.backend.tripPlan.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.user.model.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joinRequestId;
    private boolean join;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripPlanId")
    private TripPlan tripPlan;
}
