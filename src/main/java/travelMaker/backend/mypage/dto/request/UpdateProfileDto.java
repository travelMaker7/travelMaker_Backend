package travelMaker.backend.mypage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileDto {
    @Schema(description = "닉네임", example = "sosakkkkkk")
    private String nickname;

    @Schema(description = "소개글", example = "나의 MBTI는!!! 0000이야~ 같이 놀쟈")
    private String userDescription;
}
