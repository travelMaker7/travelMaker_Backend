package travelMaker.backend.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NicknameRequest {
    @Schema(description = "유저 닉네임", example = "sosak")
    private String nickname;
}
