package travelMaker.backend.tripPlan.dto.response;

import lombok.*;
import org.springframework.data.domain.PageImpl;

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
    private int totalPages;
    private int currentPages;

    public SearchRegionDto(String address, String destinationName, PageImpl<SummaryTripPlan> tripPlans) {
        this.address = address;
        this.destinationName = destinationName;
        this.schedules = tripPlans.getContent();
        this.totalPages = tripPlans.getTotalPages();
        this.currentPages = tripPlans.getNumber();
    }
}
