package travelMaker.backend.JoinRequest.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.user.model.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
@SQLDelete(sql = "UPDATE joinRequest SET isDeleted = true WHERE id = ?")
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

    private boolean isDeleted;

    @Builder
    public JoinRequest(JoinStatus joinStatus, User user, TripPlan tripPlan, boolean isDeleted) {
        this.joinStatus = joinStatus;
        this.user = user;
        this.tripPlan = tripPlan;
        this.isDeleted = isDeleted;
    }



    public void updateJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
    }
}
