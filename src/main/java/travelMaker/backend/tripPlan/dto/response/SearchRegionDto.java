package travelMaker.backend.tripPlan.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchRegionDto {

    private String address;
    private String destinationName;
    private List<SummaryTripPlan> schedules;



}
