package travelMaker.backend.schedule.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import travelMaker.backend.user.model.User;

@SQLDelete(sql = "UPDATE schedule SET is_deleted = true WHERE schedule_id = ?")
// 아래 어노테이션은 데이터베이스 레벨에서 WHERE 절을 정의하는 데 사용되는 것이 아니라, JPA 엔티티 레벨에서 조회할 때 적용된다.엔터티를 조회하는 모든 JPA 쿼리에 적용된다.
@Where(clause = "is_deleted = false") // 쿼리문에 where deleted = false를 추가해줄 어노테이션
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Schedule{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String scheduleName;

    private String scheduleDescription;

    @Column(nullable = false)
    private String chatUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Builder
    public Schedule(
            String scheduleName,
            String scheduleDescription,
            String chatUrl,
            User user,
            boolean isDeleted
    ) {
        this.scheduleName = scheduleName;
        this.scheduleDescription = scheduleDescription;
        this.chatUrl = chatUrl;
        this.user = user;
        this.isDeleted = isDeleted;
    }

}
