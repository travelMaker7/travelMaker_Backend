package travelMaker.backend.JoinRequest.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.user.model.User;

@SQLDelete(sql = "UPDATE join_request SET is_deleted = true WHERE join_id = ?")
// 아래 어노테이션은 데이터베이스 레벨에서 WHERE 절을 정의하는 데 사용되는 것이 아니라, JPA 엔티티 레벨에서 조회할 때 적용된다.엔터티를 조회하는 모든 JPA 쿼리에 적용된다.
@Where(clause = "is_deleted = false") // 쿼리문에 where deleted = false를 추가해줄 어노테이션
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
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

    @Column(columnDefinition = "boolean default false")
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
