package travelMaker.backend.user.model;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import travelMaker.backend.schedule.model.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id = ?")
// 아래 어노테이션은 데이터베이스 레벨에서 WHERE 절을 정의하는 데 사용되는 것이 아니라, JPA 엔티티 레벨에서 조회할 때 적용된다.엔터티를 조회하는 모든 JPA 쿼리에 적용된다.
@Where(clause = "is_deleted = false") // 쿼리문에 where deleted = false를 추가해줄 어노테이션
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String password;
    private String imageUrl;
    private String userName;
    private String nickname;
    private String userGender;
    @Column(unique = true, nullable = false)
    private String userEmail;
    private String userAgeRange;
    private String userDescription;
    private LocalDate signupDate;
    @ColumnDefault("36.5")
    private Double mannerScore;
    @Embedded
    private PraiseBadge praiseBadge;

    private boolean isDeleted;

    @Builder
    public User(Long userId, String password, String imageUrl, String userName, String nickname, String userGender, String userEmail, String userAgeRange, String userDescription, LocalDate signupDate, Double mannerScore, PraiseBadge praiseBadge, boolean isDeleted) {
        this.userId = userId;
        this.password = password;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.nickname = nickname;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userAgeRange = userAgeRange;
        this.userDescription = userDescription;
        this.signupDate = signupDate;
        this.mannerScore = mannerScore;
        this.praiseBadge = praiseBadge != null ? praiseBadge : new PraiseBadge(0, 0, 0, 0);
        this.isDeleted = isDeleted;
    }

    public void updateDescription(String userDescription) {
        this.userDescription = userDescription;
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePraiseBadge(
            Integer photographer,
            Integer timeIsGold,
            Integer kingOfKindness,
            Integer professionalGuide
    )
    {
        this.praiseBadge.updatePhotographer(photographer);
        this.praiseBadge.updateTimeIsGold(timeIsGold);
        this.praiseBadge.updateKingOfKindness(kingOfKindness);
        this.praiseBadge.updateProfessionalGuide(professionalGuide);
    }

    public void updateMannerScore(Double mannerScore) {
        this.mannerScore = mannerScore;
    }
}
