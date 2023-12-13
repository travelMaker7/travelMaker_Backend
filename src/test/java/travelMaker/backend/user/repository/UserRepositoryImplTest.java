package travelMaker.backend.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Rollback(value = false)
@Transactional
class UserRepositoryImplTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void getMyProfile() {
        User user = User.builder()
                .userId(1l)
                .build();
        MyProfileDto myProfile = userRepository.getMyProfile(new LoginUser(user));
        log.info("myProfile: " + myProfile);

    }

    @Test
    void getUserProfile() {
        Long targetUserId = 1l;
        User user = User.builder()
                .userId(10l)
                .build();
        UserProfileDto userProfile = userRepository.getUserProfile(targetUserId, new LoginUser(user));
        log.info("userProfile: " + userProfile);
    }
}