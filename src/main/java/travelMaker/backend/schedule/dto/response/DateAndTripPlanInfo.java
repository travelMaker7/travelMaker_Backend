package travelMaker.backend.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class DateAndTripPlanInfo {
    private LocalDate scheduledDate;
    private TripPlanInfo tripPlanInfo;
}
