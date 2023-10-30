package travelMaker.backend.JoinRequest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotificationsDto {
    @Schema(
            description = "동행 신청 알림 목록",
            example = "[{ \"joinId\": 1, \"scheduleName\": \"서울 맛집 탐방\", \"destinationName\": \"경복궁\", \"userName\": \"김소싹\" }, { ... }, { ... }]"
    )
    private List<JoinRequestNotification> notifications;
}
