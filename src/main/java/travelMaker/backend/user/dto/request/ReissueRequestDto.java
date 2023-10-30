package travelMaker.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReissueRequestDto {
    @NotBlank
    private String refreshToken;
}
