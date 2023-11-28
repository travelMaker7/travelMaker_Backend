//package travelMaker.backend.JoinRequest.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;
//import travelMaker.backend.JoinRequest.dto.request.HostJoinRequestDto;
//import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;
//import travelMaker.backend.JoinRequest.model.JoinStatus;
//import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;
//import travelMaker.backend.user.login.LoginUser;
//import travelMaker.backend.user.model.User;
//
//@SpringBootTest
//@Rollback(value = false)
//@Transactional
//class JoinRequestServiceTest {
//
//    @Autowired
//    JoinRequestService joinRequestService;
//    @Autowired
//    JoinRequestRepository joinRequestRepository;
//
//    @Test
//    @DisplayName("동행 신청")
//    public void guestJoinRequestTest() throws Exception {
//
//        //given
//        User user = User.builder()
//                .userId(16l)
//                .build();
//
//        GuestJoinRequestDto guestJoinRequestDto = GuestJoinRequestDto.builder()
//                .hostId(6l)
//                .tripPlanId(1l)
//                .joinStatus(JoinStatus.승인대기)
//                .build();
//
//        //when
//        joinRequestService.guestJoinRequest(guestJoinRequestDto, new LoginUser(user));
//
//        //then
////        JoinRequest joinRequest = joinRequestRepository.findById(11l).orElseThrow(() -> new NoSuchElementException("JoinRequest not found with ID: 1"));
////        Assertions.assertThat(joinRequest.getTripPlan().getTripPlanId()).isEqualTo(99l);
//
//    }
//
//    @Test
//    @DisplayName("동행 신청 취소")
//    public void guestJoinCancel() throws Exception {
//        //given
//        Long tripPlanId = 1l;
//
//        User guest = User.builder()
//                .userId(10l)
//                .build();
//
//        //when
//        joinRequestService.guestJoinCancel(tripPlanId, new LoginUser(guest));
//
//        //then
//    }
//
//    @Test
//    @DisplayName("동행 신청 수락/거절")
//    public void hostJoinRequestTest() throws Exception {
//
//        //given
//        HostJoinRequestDto hostJoinRequestDto = HostJoinRequestDto.builder()
//                .joinId(10l)
//                .joinStatus(JoinStatus.신청수락)
//                .build();
//
//        //when
//        joinRequestService.hostJoinRequest(hostJoinRequestDto);
//
//        //then
////        JoinRequest joinRequest = joinRequestRepository.findById(1l).orElseThrow();
////        Assertions.assertThat(joinRequest.getTripPlan().getTripPlanId()).isEqualTo(99l);
//
//    }
//
//    @Test
//    @DisplayName("동행 신청 알림")
//    public void joinRequestNotifications() throws Exception {
//
//        //given
//        User user = User.builder()
//                .userId(6l)
//                .build();
//
//        //when
//        NotificationsDto result = joinRequestService.joinRequestNotifications(new LoginUser(user));
//
//        //then
//        System.out.println(result);
//    }
//
//}
//
