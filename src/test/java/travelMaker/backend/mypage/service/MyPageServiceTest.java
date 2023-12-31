package travelMaker.backend.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.mypage.dto.request.RegisterReviewDto;
import travelMaker.backend.mypage.dto.response.JoinUsers;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.RegisteredScheduleListDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
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
        MyProfileDto myProfile = myPageService.getMyProfile(new LoginUser(user));

        // then
        System.out.println("myProfile = " + myProfile);
    }
    @Test
    @DisplayName("타인 프로필 조회")
    void getUserProfile() {
        Long targetUserId = 1l;
        User user = User.builder()
                .userId(10l)
                .build();
        UserProfileDto userProfile = userRepository.getUserProfile(targetUserId, new LoginUser(user));
        System.out.println("userProfile = " + userProfile);
    }

//    @Test
//    @DisplayName("리뷰 등록")
//    public void registerReview() throws Exception {
//        //given
//        RegisterReviewDto registerReviewDto = RegisterReviewDto.builder()
//                .tripPlanId(1l)
//                .photographer(1)
//                .timeIsGold(1)
//                .kingOfKindness(0)
//                .professionalGuide(0)
//                .mannerScore(-1.0)
//                .build();
//
//        Long reviewTargetId = 9l;
//        User user = userRepository.findById(1l).orElseThrow();
//
//        //when
//        myPageService.registerReview(registerReviewDto, reviewTargetId, new LoginUser(user));
//
//        //then
//    }

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

    @Test
    public void 등록일정_조회() throws Exception{
        //given
        RegisteredScheduleListDto registerScheduleList = myPageService.getRegisterScheduleList(new LoginUser(User.builder().userId(1L).build()));
        //when
        System.out.println("registerScheduleList = " + registerScheduleList);
        //then

    }
}