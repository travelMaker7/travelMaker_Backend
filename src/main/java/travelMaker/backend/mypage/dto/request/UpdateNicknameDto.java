package travelMaker.backend.mypage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameDto {
    @NotBlank(message = "닉네임을 작성해 주세요")
    @Schema(description = "닉네임", example = "sosakkkkkk")
    private String nickname;
}