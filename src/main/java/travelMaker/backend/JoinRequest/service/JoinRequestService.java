package travelMaker.backend.JoinRequest.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.request.HostJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;
import travelMaker.backend.JoinRequest.model.JoinRequest;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final TripPlanRepository tripPlanRepository;
    private final UserRepository userRepository;
    private final JoinRequestRepository joinRequestRepository;

    @Transactional
    public void guestJoinRequest(GuestJoinRequestDto guestJoinRequestDto) {

        Long guestId = guestJoinRequestDto.getGuestId();
        Long tripPlanId = guestJoinRequestDto.getTripPlanId();
        JoinStatus joinStatus = guestJoinRequestDto.getJoinStatus();

        TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(() -> new EntityNotFoundException("해당하는 일정 여행지가 존재하지 않습니다. ID: " + tripPlanId));
        User user = userRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("해당하는 회원이 존재하지 않습니다. ID: " + guestId));

        JoinRequest guestJoinRequest = JoinRequest.builder()
                .tripPlan(tripPlan)
                .user(user)
                .joinStatus(joinStatus)
                .build();

        // JoinRequest 엔티티 저장
        joinRequestRepository.save(guestJoinRequest);
    }

    @Transactional
    public void hostJoinRequest(HostJoinRequestDto hostJoinRequestDto) {

        Long joinId = hostJoinRequestDto.getJoinId();
        JoinStatus joinStatus = hostJoinRequestDto.getJoinStatus();

        // 기존 행 가져오기
        JoinRequest joinRequest = joinRequestRepository.findById(joinId).orElseThrow(() -> new EntityNotFoundException("해당하는 동행 신청이 존재하지 않습니다. ID: " + joinId));

        // 기존 엔티티의 값을 수정
        joinRequest.updateJoinStatus(joinStatus);

        // 수정된 엔티티를 저장
        joinRequestRepository.save(joinRequest);
    }

    @Transactional
    public NotificationsDto joinRequestNotifications(Long userId) {

        return joinRequestRepository.searchNotifications(userId);
    }
}
