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

    private Long joinId;

    private String scheduleName;

    private String destinationName;

    private String nickname;

    private JoinStatus joinStatus;
}
