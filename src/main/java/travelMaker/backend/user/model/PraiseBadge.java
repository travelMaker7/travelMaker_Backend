package travelMaker.backend.user.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
public class PraiseBadge {
    @ColumnDefault("0")
    private Integer photographer;
    @ColumnDefault("0")
    private Integer timeIsGold;
    @ColumnDefault("0")
    private Integer kingOfKindness;
    @ColumnDefault("0")
    private Integer professionalGuide;
    public PraiseBadge() {
        // 기본 생성자
    }
    public PraiseBadge(Integer photographer, Integer timeIsGold, Integer kingOfKindness, Integer professionalGuide) {
        this.photographer = photographer;
        this.timeIsGold = timeIsGold;
        this.kingOfKindness = kingOfKindness;
        this.professionalGuide = professionalGuide;
    }

    public void updatePhotographer(Integer photographer) {
        this.photographer = photographer;
    }
    public void updateTimeIsGold(Integer timeIsGold) {
        this.timeIsGold = timeIsGold;
    }
    public void updateKingOfKindness(Integer kingOfKindness) {
        this.kingOfKindness = kingOfKindness;
    }
    public void updateProfessionalGuide(Integer professionalGuide) {
        this.professionalGuide = professionalGuide;
    }

}

