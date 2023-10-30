package travelMaker.backend.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String email;
    private String imageUrl;
    private String username;
    private String ageRange;
    private String gender;
    private String accessToken;
    private String refreshToken;
}