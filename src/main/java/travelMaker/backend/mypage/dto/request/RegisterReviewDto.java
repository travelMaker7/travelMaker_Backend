package travelMaker.backend.mypage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReviewDto {

    @Min(value = 0, message = "photographer는 0 이상의 값을 가져야 합니다.")
    @Schema(description = "칭찬배지: 포토그래퍼", example = "0")
    private Integer photographer;

    @Min(value = 0, message = "timeIsGold는 0 이상의 값을 가져야 합니다.")
    @Schema(description = "칭찬배지: 시간은 금", example = "0")
    private Integer timeIsGold;

    @Min(value = 0, message = "kingOfKindness는 0 이상의 값을 가져야 합니다.")
    @Schema(description = "칭찬배지: 친절대마왕", example = "1")
    private Integer kingOfKindness;

    @Min(value = 0, message = "professionalGuide는 0 이상의 값을 가져야 합니다.")
    @Schema(description = "칭찬배지: 여행 가이드 뺨침", example = "2")
    private Integer professionalGuide;

    @DecimalMin(value = "0.0", message = "mannerScore는 0 이상의 값을 가져야 합니다.")
    @Schema(description = "매너온도", example = "36.8")
    private Double mannerScore;

}
