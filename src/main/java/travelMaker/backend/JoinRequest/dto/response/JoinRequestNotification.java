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

    @Schema(description = "동행 신청 식별 번호", example = "10")
    private Long joinId;

    @Schema(description = "일정명", example = "서울 맛집 탐방")
    private String scheduleName;

    @Schema(description = "일정 여행지 이름", example = "경복궁")
    private String destinationName;

    @Schema(description = "동행 신청자 이름", example = "김소싹")
    private String userName;
}
