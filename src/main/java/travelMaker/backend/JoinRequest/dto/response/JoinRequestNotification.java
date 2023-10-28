package travelMaker.backend.JoinRequest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import travelMaker.backend.JoinRequest.model.JoinStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinRequestNotification {

    @NotNull(message = "동행 신청 식별번호가 있어야 합니다.")
    @Schema(description = "동행 신청 식별 번호", example = "10")
    private Long joinId;

    @NotBlank(message = "일정명이 있어야 합니다.")
    @Schema(description = "일정명", example = "서울 맛집 탐방")
    private String scheduleName;

    @NotBlank(message = "일정 여행지 이름이 있어야 합니다.")
    @Schema(description = "일정 여행지 이름", example = "경복궁")
    private String destinationName;

    @NotBlank(message = "동행을 신청한 사용자 이름이 있어야 합니다.")
    @Schema(description = "동행 신청자 이름", example = "김소싹")
    private String userName;
}
