package travelMaker.backend.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import travelMaker.backend.user.model.PlatformType;
import travelMaker.backend.user.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@NoArgsConstructor
@Getter
@Setter
public class SignupRequestDto {

    @Schema(description = "유저 이름", example = "트메")
    @NotEmpty(message = "이름 입력은 필수 입니다.")
    private String name;

    @Schema(description = "유저 비밀번호", example = "hello123")
    @NotEmpty(message = "비밀번호 입력은 필수 입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9]).{8,12}$", message = "유효하지 않은 비밀번호 형식입니다.")
    private String password;

    @Schema(description = "유저 닉네임", example = "sosak")
    @NotEmpty(message = "닉네임 입력은 필수 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9가-힇]{2,10}$", message = "유효하지 않은 닉네임 형식입니다.")
    private String nickname;

    @Schema(description = "유저 성별", example = "여성")
    @NotEmpty(message = "성별 입력은 필수 입니다.")
    private String gender;

    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Schema(description = "유저 이메일", example = "sosak@gmail.com")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotNull(message = "생년월일을 입력해 주세요")
    @Schema(description = "생년월일", example = "2023-12-31")
    private String birth;

    @Schema(description = "이미지", example = "")
    private String imageUrl;
    private boolean emailValid;
    private boolean nicknameValid;
    @Builder
    public SignupRequestDto(String name, String password, String nickname, String gender, String email, String birth, String imageUrl, boolean emailValid, boolean nicknameValid) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.email = email;
        this.birth = birth;
        this.imageUrl = imageUrl;
        this.emailValid = emailValid;
        this.nicknameValid = nicknameValid;
    }

    public User toEntity(SignupRequestDto dto){
        return User.builder()
                .userEmail(dto.getEmail())
                .password(dto.getPassword())
                .imageUrl(dto.getImageUrl())
                .userName(dto.getName())
                .userAgeRange(getAgeRange(dto.getBirth()))
                .userGender(dto.getGender())
                .nickname(dto.getNickname())
                .signupDate(LocalDate.now())
                .birth(dateConverter(dto.getBirth()))
                .platformType(PlatformType.COMMON)
                .build();
    }
    private String getAgeRange(String birth){
        // 현재 년도 구하기
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        // 태어난 년도
        int birthYear = Integer.parseInt(birth.substring(0, 4));

        // 현재 년도 - 태어난 년도 : 나이
        int age = currentYear - birthYear + 1;
        if(90 <= age && age < 100) return "90대";
        else if(80 <= age && age < 90) return "80대";
        else if(70 <= age && age < 80) return "70대";
        else if(60 <= age && age < 70) return "60대";
        else if(50 <= age && age < 60) return "50대";
        else if(40 <= age && age < 50) return "40대";
        else if(30 <= age && age < 40) return "30대";
        else if(20 <= age && age < 30) return "20대";
        else return "10대";
    }
    private LocalDate dateConverter(String birth){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birth, format);
    }
}
