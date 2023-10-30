package travelMaker.backend.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class TripPlanDetails {

    @Schema(description = "일정 여행지 식별번호", example = "1")
    private Long tripPlanId;

    @Schema(description = "장소 이름", example = "파리")
    private String destinationName;

    @Schema(description = "동행 희망 인원", example = "5")
    private Integer wishCnt;

    @Schema(description = "동행 희망 여부", example = "true")
    private boolean wishJoin;

    @Schema(description = "여행 계획 주소", example = "123 Main St, City")
    private String address;

    @Schema(description = "도착 시간", example = "09:00")
    private LocalTime arriveTime;

    @Schema(description = "떠나는 시간", example = "18:00")
    private LocalTime leaveTime;
}
