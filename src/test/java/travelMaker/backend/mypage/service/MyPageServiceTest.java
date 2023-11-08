package travelMaker.backend.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.mypage.dto.request.RegisterReviewDto;
import travelMaker.backend.mypage.dto.response.JoinUsers;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
class MyPageServiceTest {

    @Autowired
    MyPageService myPageService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("본인 프로필 조회")
    public void getMyProfile() {
        // give
        User user = userRepository.findById(1l).orElseThrow();

        // when
        myPageService.getMyProfile(new LoginUser(user));

        // then
    }

    @Test
    @DisplayName("리뷰 등록")
    public void registerReview() throws Exception {
        //given
        RegisterReviewDto registerReviewDto = RegisterReviewDto.builder()
                .photographer(1)
                .timeIsGold(1)
                .kingOfKindness(0)
                .professionalGuide(0)
                .mannerScore(-1.0)
                .build();

        Long scheduleId = 1l;

        //when
        myPageService.registerReview(registerReviewDto, scheduleId);

        //then
    }

    @Test
    @DisplayName("동행 인원 조회")
    public void  getUserList() throws Exception{
        //given

        Long scheduleId = 6L;
        Long tripPlanId = 1L;
        //when
        JoinUsers joinUserList = myPageService.getJoinUserList(scheduleId, tripPlanId);
        //then
        System.out.println(joinUserList);

    }
}