package travelMaker.backend.tripPlan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SummaryTripPlan{
    private String username;
    private Long scheduleId;
    private LocalDate scheduledDate;
    private LocalTime arriveTime;
    private LocalTime leaveTime;


}