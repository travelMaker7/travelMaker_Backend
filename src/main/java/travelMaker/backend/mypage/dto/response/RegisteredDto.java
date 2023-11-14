package travelMaker.backend.mypage.dto.response;

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
public class RegisteredDto {
    private List<RegisterScheduleDto> schedules;

    @Getter
    @AllArgsConstructor
    @ToString
    public static class RegisterScheduleDto {
        private Long scheduleId;
        private String scheduleName;
        private String scheduleDescription;
        private String nickname;
    }
}
