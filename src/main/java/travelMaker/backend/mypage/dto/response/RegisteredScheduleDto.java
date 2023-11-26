package travelMaker.backend.mypage.dto.response;

import lombok.*;
import travelMaker.backend.schedule.dto.response.DetailsMarker;
import travelMaker.backend.schedule.model.Schedule;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisteredScheduleDto {
    private Long scheduleId;
    private String scheduleName;
    private String scheduleDescription;
    private List<TripPlanMarker> markers;

    public RegisteredScheduleDto(Schedule schedule, List<TripPlanMarker> markers) {
        this.scheduleId = schedule.getScheduleId();
        this.scheduleName = schedule.getScheduleName();
        this.scheduleDescription = schedule.getScheduleDescription();
        this.markers = markers;
    }
}