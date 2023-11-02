package travelMaker.backend.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
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
    private Long mannerScore;
    @Embedded
    private PraiseBadge praiseBadge;

    @Builder
    public User(Long userId, String password, String imageUrl, String userName, String nickname, String userGender, String userEmail, String userAgeRange, String userDescription, LocalDate signupDate, Long mannerScore, PraiseBadge praiseBadge) {
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
}
