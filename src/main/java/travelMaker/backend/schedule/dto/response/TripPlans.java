package travelMaker.backend.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class TripPlans {

    private LocalDate scheduledDate;

    private List<TripPlanDetails> tripPlanDetails;

}
