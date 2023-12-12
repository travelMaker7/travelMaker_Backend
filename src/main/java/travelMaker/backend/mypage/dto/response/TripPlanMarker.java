package travelMaker.backend.mypage.dto.response;

import lombok.*;
import travelMaker.backend.tripPlan.model.TripPlan;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TripPlanMarker {
    private Double destinationX;
    private Double destinationY;

    public TripPlanMarker(TripPlan tripPlan){
        this.destinationX = tripPlan.getDestinationX();
        this.destinationY = tripPlan.getDestinationY();
    }
}
