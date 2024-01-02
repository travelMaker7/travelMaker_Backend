package travelMaker.backend.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendEmailRequestDto {

    @Schema(description = "유저 이메일", example = "sosak@gmail.com")
    private String email;

    @Builder
    public SendEmailRequestDto(String email) {
        this.email = email;
    }
}
