package travelMaker.backend.mypage.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class RegisteredScheduleListDto {
    private List<RegisteredScheduleDto> schedules;

    public RegisteredScheduleListDto(List<RegisteredScheduleDto> schedules){
        this.schedules = schedules;
    }
}
