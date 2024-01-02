package travelMaker.backend.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.user.dto.request.EmailCheckRequestDto;
import travelMaker.backend.user.dto.request.SendEmailRequestDto;
import travelMaker.backend.user.dto.request.SignupRequestDto;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입")
    public void test() {
        SignupRequestDto dto = SignupRequestDto.builder()
                .email("sosak@gmail.com")
                .password("hello123")
                .nickname("sosak")
                .gender("여성")
                .birth("1998-12-03")
                .emailValid(true)
                .nicknameValid(true)
                .build();
//        assertThatThrownBy(()->userService.signup(dto)).isInstanceOf(GlobalException.class);
    }

    @Test
    @DisplayName("이메일 발송 테스트")
    public void sendEmail() {
        SendEmailRequestDto dto = SendEmailRequestDto.builder().email("sosak@gmail.com").build();

        ResponseDto<Void> response = userService.sendMail(dto);
        assertThat(response.getMessage()).isEqualTo("인증 메일 발송 완료");
    }

    @Test
    @DisplayName("이메일 인증 테스트")
    public void checkEmail() {
        EmailCheckRequestDto request = EmailCheckRequestDto.builder()
                .code("951362")
                .email("sosak@gmail.com")
                .build();
        userService.emailCheck(request);
    }

}