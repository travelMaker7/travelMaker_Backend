package travelMaker.backend.JoinRequest.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.user.model.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long joinId;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripPlanId")
    private TripPlan tripPlan;

    @Builder
    public JoinRequest(TripPlan tripPlan, User user, JoinStatus joinStatus) {
        this.tripPlan = tripPlan;
        this.user = user;
        this.joinStatus = joinStatus;
    }
}
