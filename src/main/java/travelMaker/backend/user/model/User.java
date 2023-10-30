package travelMaker.backend.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String userGender;
    private String userEmail;
    private String userAgeRange;
    private LocalDate signupDate;

    @Builder
    public User(Long userId, String password,String imageUrl, String userName, String userGender, String userEmail, String userAgeRange, LocalDate signupDate) {
        this.userId = userId;
        this.password = password;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userAgeRange = userAgeRange;
        this.signupDate = signupDate;
    }

}
