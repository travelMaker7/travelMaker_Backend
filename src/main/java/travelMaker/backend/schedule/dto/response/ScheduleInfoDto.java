package travelMaker.backend.schedule.dto.response;

import lombok.*;
import travelMaker.backend.schedule.model.Schedule;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ScheduleInfoDto {

    private Long scheduleId;
    private String scheduleName;
    private List<DayByTripPlans> tripPlans;
    private String scheduleDescription;
    private String chatUrl;



    public static ScheduleInfoDto from(List<DayByTripPlans> dayByTripPlans, Schedule schedule){
        return ScheduleInfoDto.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleName(schedule.getScheduleName())
                .tripPlans(dayByTripPlans)
                .scheduleDescription(schedule.getScheduleDescription())
                .chatUrl(schedule.getChatUrl())
                .build();
    }

    public ScheduleInfoDto(Schedule schedule, List<DayByTripPlans> tripPlans){
        this.scheduleId = schedule.getScheduleId();
        this.scheduleName = schedule.getScheduleName();
        this.tripPlans = tripPlans;
        this.scheduleDescription = schedule.getScheduleDescription();
        this.chatUrl = schedule.getChatUrl();
    }
}
