package travelMaker.backend.JoinRequest.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.response.JoinRequestNotification;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;
import travelMaker.backend.JoinRequest.model.JoinRequest;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Rollback(value = false)
class JoinRequestRepositoryImplTest {

    @Autowired
    JoinRequestRepository joinRequestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TripPlanRepository tripPlanRepository;

    @Test
    @DisplayName("알림 조회")
    public void searchNotification() throws Exception{
        //given
        User user = User.builder()
                .userId(1L)
                .nickname("sosak")
                .userEmail("sosak@gmail.com")
                .password("123")
                .build();

        //when
        NotificationsDto notificationsDto = joinRequestRepository.searchNotifications(user.getUserId());
        //then

        for (JoinRequestNotification notification : notificationsDto.getNotifications()) {
            System.out.println(notification);
        }
    }

    @Test
    @DisplayName("조인 신청")
    public void guestJoinRequest() throws Exception{
        //given

        TripPlan tripPlan = tripPlanRepository.findById(1L).get();
        User user = userRepository.findById(1L).get();
        User user2 = userRepository.findById(2L).get();

        JoinRequest join = JoinRequest.builder()
                .tripPlan(tripPlan)
                .user(user)
                .joinStatus(JoinStatus.승인대기).build();
        JoinRequest join2 = JoinRequest.builder()
                .tripPlan(tripPlan)
                .user(user2)
                .joinStatus(JoinStatus.승인대기).build();

        joinRequestRepository.save(join);
        joinRequestRepository.save(join2);

        //when
        JoinRequest result = joinRequestRepository.findByTripPlanIdAndUserId(1L, 1L);
        JoinRequest result2 = joinRequestRepository.findByTripPlanIdAndUserId(1L, 2L);
        //then
        System.out.println("1번 : "+result.getJoinId());
        System.out.println("2번 : "+result2.getJoinId());

    }

}