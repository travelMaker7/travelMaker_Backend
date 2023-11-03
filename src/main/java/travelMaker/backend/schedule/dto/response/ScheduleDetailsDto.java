package travelMaker.backend.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleDetailsDto {

    private Long scheduleId;

    private List<DetailsMarker> markers;

    private String scheduleName;

    private LocalDate startDate;

    private LocalDate finishDate;

    private List<TripPlans> tripPlans;

    private String chatUrl;


}
