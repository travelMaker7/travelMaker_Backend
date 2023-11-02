package travelMaker.backend.user.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class PraiseBadge {
    private Integer photographer;
    private Integer timeIsGold;
    private Integer kingOfKindness;
    private Integer professionalGuide;

}
