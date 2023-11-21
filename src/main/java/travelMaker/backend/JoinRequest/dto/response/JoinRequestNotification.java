package travelMaker.backend.JoinRequest.dto.response;

import lombok.*;
import travelMaker.backend.JoinRequest.model.JoinStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinRequestNotification {

    private Long joinId;

    private String scheduleName;

    private String destinationName;

    private String nickname;

    private JoinStatus joinStatus;
}
