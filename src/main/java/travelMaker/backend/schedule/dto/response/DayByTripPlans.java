package travelMaker.backend.schedule.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class DayByTripPlans {

    private LocalDate scheduledDate;
    private List<TripPlanInfo> tripPlanDetails;

    public DayByTripPlans(LocalDate scheduledDate, List<TripPlanInfo> tripPlanInfos) {
        this.scheduledDate = scheduledDate;
        this.tripPlanDetails = tripPlanInfos;
    }
}