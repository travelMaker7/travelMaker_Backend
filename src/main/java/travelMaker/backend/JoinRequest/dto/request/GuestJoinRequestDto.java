package travelMaker.backend.JoinRequest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.JoinRequest.model.JoinStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestJoinRequestDto {

    @NotNull(message = "")
    @Schema(description = "일정 여행지 식별번호", example = "34")
    private Long tripPlanId;

    @NotNull(message = "")
    @Schema(description = "동행 신청자", example = "3")
    private Long guestId;

    @NotBlank(message = "동행 신청/신청 취소 중 하나의 상태값이 있어야 합니다.")
    @Schema(description = "동행 상태", example = "동행신청(or 신청취소)")
    private JoinStatus joinStatus;

}
