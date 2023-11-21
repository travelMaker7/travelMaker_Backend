package travelMaker.backend.JoinRequest.model;

import jakarta.persistence.*;
import lombok.*;
import travelMaker.backend.user.model.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private Long joinId;

    @Column(nullable = false)
    private String scheduleName;

    @Column(nullable = false)
    private String destinationName;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @Builder
    public Notifications(
            User user,
            Long joinId,
            String scheduleName,
            String destinationName,
            String nickname,
            JoinStatus joinStatus) {
        this.user = user;
        this.joinId = joinId;
        this.scheduleName = scheduleName;
        this.destinationName = destinationName;
        this.nickname = nickname;
        this.joinStatus = joinStatus;
    }
}
