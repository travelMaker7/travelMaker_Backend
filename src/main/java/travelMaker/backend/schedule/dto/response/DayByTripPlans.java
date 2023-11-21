package travelMaker.backend.schedule.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class DayByTripPlans {

    private LocalDate scheduledDate;
    private List<TripPlanInfo> tripPlanDetails;


}