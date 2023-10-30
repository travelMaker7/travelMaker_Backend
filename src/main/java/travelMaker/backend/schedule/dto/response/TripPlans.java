package travelMaker.backend.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class TripPlans {

    @Schema(description = "일정 날짜", example = "2023-10-31")
    private LocalDate scheduledDate;

    @Schema(description = "여행 계획 상세 정보", example = "[{...}, {...}]")
    private List<TripPlanDetails> tripPlanDetails;

}
