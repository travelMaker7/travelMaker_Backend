package travelMaker.backend.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.user.dto.request.SignupRequestDto;

import static org.assertj.core.api.Assertions.*;


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
                .imageUrl("")
                .birth("1998-12-03")
                .emailValid(true)
                .nicknameValid(true)
                .build();
//        assertThatThrownBy(()->userService.signup(dto)).isInstanceOf(GlobalException.class);
    }


}