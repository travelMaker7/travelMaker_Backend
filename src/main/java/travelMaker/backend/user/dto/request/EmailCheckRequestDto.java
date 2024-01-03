package travelMaker.backend.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailCheckRequestDto {
    @Schema(description = "이메일 인증 코드", example = "123456")
    private String code;
    @Schema(description = "유저 이메일", example = "sosak@gmail.com")
    private String email;

    @Builder
    public EmailCheckRequestDto(String code, String email) {
        this.code = code;
        this.email = email;
    }
}
