package travelMaker.backend.mypage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDescriptionDto {

    @NotBlank(message = "소개글을 작성해 주세요")
    @Schema(description = "소개글", example = "나의 MBTI는!!! 0000이야~ 같이 놀쟈")
    private String userDescription;
}
