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
public class HostJoinRequestDto {

    @NotNull(message = "")
    @Schema(description = "동행 신청 식별번호", example = "3")
    private Long joinId;

    @NotBlank(message = "신청수락/신청거절 중 하나의 상태값이 있어야 합니다.")
    @Schema(description = "동행 상태", example = "신청수락(or 신청거절)")
    private JoinStatus joinStatus;


}
