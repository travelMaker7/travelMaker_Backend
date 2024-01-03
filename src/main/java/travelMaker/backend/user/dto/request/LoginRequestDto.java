package travelMaker.backend.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {

    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Schema(description = "유저 이메일", example = "sosak@gmail.com")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @Schema(description = "유저 비밀번호", example = "hello123")
    @NotEmpty(message = "비밀번호 입력은 필수 입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9]).{8,12}$", message = "유효하지 않은 비밀번호 형식입니다.")
    private String password;
}
