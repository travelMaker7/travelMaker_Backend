package travelMaker.backend.JoinRequest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.request.HostJoinRequestDto;
import travelMaker.backend.JoinRequest.model.JoinRequest;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
class JoinRequestServiceTest {

    @Autowired
    JoinRequestService joinRequestService;
    @Autowired
    JoinRequestRepository joinRequestRepository;

    @Test
    @DisplayName("동행 신청/취소")
    public void guestJoinRequestTest() {

        //given
        //해당하는 일정여행지, 유저 엔티티가 TripPlanRepository와 UserRepository에 저장되어 DB에 있어야 함
        // -> 데이터그립에서 sql문으로 만들어줬음

        GuestJoinRequestDto guestJoinRequestDto = GuestJoinRequestDto.builder()
                .tripPlanId(99l)
                .guestId(98l)
                .joinStatus(JoinStatus.승인대기)
                .build();

        //when
        joinRequestService.guestJoinRequest(guestJoinRequestDto);

        //then
        JoinRequest joinRequest = joinRequestRepository.findById(11l).orElseThrow(() -> new NoSuchElementException("JoinRequest not found with ID: 1"));
        Assertions.assertThat(joinRequest.getTripPlan().getTripPlanId()).isEqualTo(99l);

    }

    @Test
    @DisplayName("동행 신청 수락/거절")
    public void hostJoinRequestTest() {

        //given
        HostJoinRequestDto hostJoinRequestDto = HostJoinRequestDto.builder()
                .joinId(11l)
                .joinStatus(JoinStatus.신청거절)
                .build();

        //when
        joinRequestService.hostJoinRequest(hostJoinRequestDto);

        //then
        JoinRequest joinRequest = joinRequestRepository.findById(11l).orElseThrow();
        Assertions.assertThat(joinRequest.getTripPlan().getTripPlanId()).isEqualTo(99l);

    }

}