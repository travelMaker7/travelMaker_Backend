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

    @Schema(description = "일정 식별번호", example = "1")
    private Long scheduleId;

    @Schema(description = "일정 상세보기 마커 목록", example = "[{...}, {...}]")
    private List<DetailsMarker> markers;

    @Schema(description = "일정명", example = "서울 여행")
    private String scheduleName;

    @Schema(description = "시작일", example = "2023-11-01")
    private LocalDate startDate;

    @Schema(description = "종료일", example = "2023-11-10")
    private LocalDate finishDate;

    @Schema(description = "여행 계획 목록", example = "[{...}, {...}]")
    private List<TripPlans> tripPlans;

    @Schema(description = "오픈채팅방", example = "https://open.kakao.com/o/s5E3AYof")
    private String chatUrl;

}
