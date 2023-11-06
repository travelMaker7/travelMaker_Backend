package travelMaker.backend.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import travelMaker.backend.schedule.model.Date;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.time.LocalTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class DestinationDetail{
    @Schema(description = "여행지 장소명", example = "한라산")
    @NotBlank(message = "장소명을 입력해 주세요")
    private String destinationName;

    @Schema(description = "동행 희망 인원", example = "2")
    private Integer wishCnt;

    @Schema(description = "동행 희망 여부", example = "true")
    @NotNull(message = "동행희망 여부를 입력해 주세요")
    private boolean wishJoin;

    @Schema(description = "여행지 주소", example = "제주특별자치도 제주시 오등동 1100로 2070-61")
    @NotBlank(message = "주소를 입력해 주세요")
    private String address;

    @Schema(description = "여행지 도착 시간", example = "12:00:00")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime arriveTime;

    @Schema(description = "여행지 떠나는 시간", example = "13:00:00")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime leaveTime;

    @Schema(description = "경도", example = "126.123")
    @NotNull(message = "장소의 경도를 입력해 주세요")
    @Digits(integer = 3, fraction = 6)
    @DecimalMin(value = "125.0", message = "경도는 125.0 이상이어야 합니다.")
    @DecimalMax(value = "132.0", message = "경도는 132.0 이하이어야 합니다.")
    private Double destinationX;

    @Schema(description = "위도", example = "33.123")
    @NotNull(message = "장소의 위도를 입력해 주세요")
    @Digits(integer = 2, fraction = 6)
    @DecimalMin(value = "33.0", message = "위도는 33.0 이상이어야 합니다.")
    @DecimalMax(value = "39.0", message = "위도는 39.0 이하이어야 합니다.")
    private Double destinationY;

    @Schema(description = "지역", example = "제주도")
    private String region;


    public TripPlan toTripPlanEntity(Date date){
        return TripPlan.builder()
                .destinationName(this.destinationName)
                .wishCnt(this.wishCnt)
                .wishJoin(this.wishJoin)
                .address(this.address)
                .region(this.region)
                .destinationX(this.destinationX)
                .destinationY(this.destinationY)
                .date(date)
                .build();
    }
}
