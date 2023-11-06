package travelMaker.backend.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import travelMaker.backend.schedule.model.Schedule;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRegisterDto {
    @NotBlank(message = "일정명을 입력해주세요.")
    @Schema(description = "일정 명", example = "소싹이의 제주동쪽 여행")
    private String scheduleName;

    @Schema(description = "일정 시작 날짜", example = "2024-05-10")
    @NotNull(message = "일정 시작일을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "일정 종료 날짜", example = "2024-05-15")
    @NotNull(message = "일정 종료일을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;

    @Valid
    @NotEmpty(message = "여행할 도시의 일정을 최소 한 개 이상 입력해 주세요.")
    private List<DailySchedule> schedules;

    @Schema(description = "동행자를 구하는 소개글", example = "나랑 함께 하면 인생샷! 건질수 있다?")
    private String scheduleDescription;

    @Schema(description = "오픈채팅방 주소", example = "https://open.kakao.com/o/s5E3AYof")
    @URL(message = "올바른 URL 형식이 아닙니다.")
    private String chatUrl;

    public Schedule toScheduleEntity(){
        return Schedule.builder()
                .scheduleName(this.scheduleName)
                .scheduleDescription(this.scheduleDescription)
                .startDate(this.startDate)
                .finishDate(this.finishDate)
                .chatUrl(this.chatUrl)
                .build();
    }


}
