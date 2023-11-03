package travelMaker.backend.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

}
