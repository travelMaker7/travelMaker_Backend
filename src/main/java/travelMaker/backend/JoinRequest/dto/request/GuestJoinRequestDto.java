package travelMaker.backend.JoinRequest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import travelMaker.backend.JoinRequest.model.JoinStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestJoinRequestDto {

    @NotNull(message = "사용자(host) 식별번호가 있어야 합니다.")
    @Positive(message = "사용자 식별번호는 양의 정수여야 합니다.")
    @Schema(description = "사용자(host) 식별번호", example = "11")
    private Long hostId;

    @NotNull(message = "일정 여행지 식별번호가 있어야 합니다.")
    @Positive(message = "일정 여행지 식별번호는 양의 정수여야 합니다.")
    @Schema(description = "일정 여행지 식별번호", example = "34")
    private Long tripPlanId;

    @NotNull(message = "동행 상태가 승인대기로 있어야 합니다.")
    @Schema(description = "동행 상태", example = "승인대기")
    private JoinStatus joinStatus;

}
