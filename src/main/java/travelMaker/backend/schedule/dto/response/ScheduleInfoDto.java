package travelMaker.backend.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ScheduleInfoDto {

    private Long scheduleId;
    private String scheduleName;
    private LocalDate startDate;
    private LocalDate finishDate;
    private List<DayByTripPlan> tripPlans;
    private String chatUrl;



}
