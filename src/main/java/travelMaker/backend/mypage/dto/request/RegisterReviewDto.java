package travelMaker.backend.mypage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterReviewDto {

    @NotNull(message = "일정 여행지 식별번호가 있어야 합니다.")
    @Positive(message = "일정 여행지 식별번호는 양의 정수여야 합니다.")
    @Schema(description = "일정 여행지 식별번호", example = "1")
    private Long tripPlanId;

    @Min(value = 0, message = "photographer는 0 이상의 값을 가져야 합니다.")
    @Max(value = 1, message = "photographer는 1 이하의 값을 가져야 합니다.")
    @NotNull(message = "photographer는 필수 필드입니다.")
    @Schema(description = "칭찬배지: 포토그래퍼", example = "0")
    private Integer photographer;

    @Min(value = 0, message = "timeIsGold는 0 이상의 값을 가져야 합니다.")
    @Max(value = 1, message = "timeIsGold는 1 이하의 값을 가져야 합니다.")
    @NotNull(message = "timeIsGold는 필수 필드입니다.")
    @Schema(description = "칭찬배지: 시간은 금", example = "0")
    private Integer timeIsGold;

    @Min(value = 0, message = "kingOfKindness는 0 이상의 값을 가져야 합니다.")
    @Max(value = 1, message = "kingOfKindness는 1 이하의 값을 가져야 합니다.")
    @NotNull(message = "kingOfKindness는 필수 필드입니다.")
    @Schema(description = "칭찬배지: 친절대마왕", example = "1")
    private Integer kingOfKindness;

    @Min(value = 0, message = "professionalGuide는 0 이상의 값을 가져야 합니다.")
    @Max(value = 1, message = "professionalGuide는 1 이하의 값을 가져야 합니다.")
    @NotNull(message = "professionalGuide는 필수 필드입니다.")
    @Schema(description = "칭찬배지: 여행 가이드 뺨침", example = "1")
    private Integer professionalGuide;

    @DecimalMin(value = "-2.0", message = "mannerScore는 -2 이상의 값을 가져야 합니다.")
    @DecimalMax(value = "2.0", message = "mannerScore는 2 이하의 값을 가져야 합니다.")
    @NotNull(message = "mannerScore는 필수 필드입니다.")
    @Schema(description = "매너온도", example = "1")
    private Double mannerScore;

}
