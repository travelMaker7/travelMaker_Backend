package travelMaker.backend.user.model;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

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

    @Builder
    public User(Long userId, String password, String imageUrl, String userName, String nickname, String userGender, String userEmail, String userAgeRange, String userDescription, LocalDate signupDate, Double mannerScore, PraiseBadge praiseBadge) {
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
        this.praiseBadge = praiseBadge;
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
