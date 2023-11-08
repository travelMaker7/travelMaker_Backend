package travelMaker.backend.JoinRequest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import travelMaker.backend.JoinRequest.model.JoinStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostJoinRequestDto {

    @NotNull(message = "동행 신청 식별번호가 있어야 합니다.")
    @Positive(message = "동행 신청 식별번호는 양의 정수여야 합니다.")
    @Schema(description = "동행 신청 식별번호", example = "3")
    private Long joinId;

    @NotNull(message = "신청수락/신청거절 중 하나의 상태값이 있어야 합니다.")
    @Schema(description = "동행 상태", example = "신청수락(or 신청거절)")
    private JoinStatus joinStatus;

    @NotNull(message = "인원 초과 여부있어야 합니다.")
    @Schema(description = "인원 초과 여부(true면 버튼 비활성화)", example = "false")
    private boolean overWish;

//    public void setOverWish(boolean overWish) {
//        this.overWish = joinCnt >= wishCnt ? true : false;
//    }

}
