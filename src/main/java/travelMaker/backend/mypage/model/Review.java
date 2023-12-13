package travelMaker.backend.mypage.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import travelMaker.backend.user.model.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @ColumnDefault("0")
    private Double mannerScore;
    @ColumnDefault("0")
    private Integer photographer;
    @ColumnDefault("0")
    private Integer timeIsGold;
    @ColumnDefault("0")
    private Integer kingOfKindness;
    @ColumnDefault("0")
    private Integer professionalGuide;
    private Long tripPlanId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewerId")
    private User reviewer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewTargetId")
    private User reviewTarget;

    @Builder
    public Review(Long reviewId, Double mannerScore, Integer photographer, Integer timeIsGold, Integer kingOfKindness, Integer professionalGuide, Long tripPlanId, User reviewer, User reviewTarget) {
        this.reviewId = reviewId;
        this.mannerScore = mannerScore;
        this.photographer = photographer;
        this.timeIsGold = timeIsGold;
        this.kingOfKindness = kingOfKindness;
        this.professionalGuide = professionalGuide;
        this.tripPlanId = tripPlanId;
        this.reviewer = reviewer;
        this.reviewTarget = reviewTarget;
    }
}
