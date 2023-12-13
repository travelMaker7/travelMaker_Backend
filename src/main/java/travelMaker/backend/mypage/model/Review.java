package travelMaker.backend.mypage.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import travelMaker.backend.user.model.PraiseBadge;
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
    @Enumerated(value = EnumType.STRING)
    private PraiseBadge praiseBadge;
    private Long tripPlanId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewerId")
    private User reviewer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewTargetId")
    private User reviewTarget;

    @Builder
    public Review(Long reviewId, Double mannerScore, PraiseBadge praiseBadge, Long tripPlanId, User reviewer, User reviewTarget) {
        this.reviewId = reviewId;
        this.mannerScore = mannerScore;
        this.praiseBadge = praiseBadge/* != null? praiseBadge : new PraiseBadge(0, 0, 0, 0)*/;
        this.tripPlanId = tripPlanId;
        this.reviewer = reviewer;
        this.reviewTarget = reviewTarget;
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
