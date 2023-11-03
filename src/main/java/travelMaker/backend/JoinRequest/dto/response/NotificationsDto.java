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

    private List<JoinRequestNotification> notifications;
}
