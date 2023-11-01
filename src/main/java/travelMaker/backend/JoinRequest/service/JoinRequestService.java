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
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final TripPlanRepository tripPlanRepository;
    private final UserRepository userRepository;
    private final JoinRequestRepository joinRequestRepository;

    @Transactional
    public void guestJoinRequest(GuestJoinRequestDto guestJoinRequestDto, LoginUser loginUser) {


        Long guestId = loginUser.getUser().getUserId();
        Long tripPlanId = guestJoinRequestDto.getTripPlanId();
        JoinStatus joinStatus = guestJoinRequestDto.getJoinStatus();

        // 동행 신청 중복 검증
        JoinRequest existingJoinRequest = joinRequestRepository.findByTripPlanIdAndUserId(tripPlanId, guestId);

        if (existingJoinRequest != null) {
            throw new GlobalException(ErrorCode.DUPLICATE_JOIN_REQUEST);
        } else {
            TripPlan tripPlan = tripPlanRepository.findById(tripPlanId)
                    .orElseThrow(() -> new GlobalException(ErrorCode.TRIP_PLAN_NOT_FOUND));

            JoinRequest guestJoinRequest = JoinRequest.builder()
                    .tripPlan(tripPlan)
                    .joinStatus(joinStatus)
                    .build();

            // JoinRequest 엔티티 저장
            joinRequestRepository.save(guestJoinRequest);


        }
    }

    @Transactional
    public void hostJoinRequest(HostJoinRequestDto hostJoinRequestDto) {

        Long joinId = hostJoinRequestDto.getJoinId();
        JoinStatus joinStatus = hostJoinRequestDto.getJoinStatus();

        if (joinStatus == JoinStatus.신청수락) {
            // JoinRequest에서 TripPlan 엔티티를 꺼내고 joinCnt를 1 증가
            JoinRequest joinRequest = joinRequestRepository.findById(joinId).orElseThrow(() -> new GlobalException(ErrorCode.JOIN_REQUEST_NOT_FOUND));
            TripPlan tripPlan = joinRequest.getTripPlan();
            tripPlan.increaseJoinCnt(tripPlan.getJoinCnt());

            // TripPlan 엔티티의 joinCnt와 wishCnt 비교, 'joinCnt > wishCnt' 일 경우 에러
            if (tripPlan.getJoinCnt() > tripPlan.getWishCnt()) {
                throw new GlobalException(ErrorCode.JOIN_CNT_EXCEEDS_WISH_CNT);
            } else {
                joinRequest.updateJoinStatus(joinStatus);
                joinRequestRepository.save(joinRequest);
            }
        }

        if (joinStatus == JoinStatus.신청거절) {
            JoinRequest joinRequest = joinRequestRepository.findById(joinId).orElseThrow(() -> new GlobalException(ErrorCode.JOIN_REQUEST_NOT_FOUND));
            joinRequest.updateJoinStatus(joinStatus);
            joinRequestRepository.save(joinRequest);
        }

    }

    @Transactional(readOnly = true)
    public NotificationsDto joinRequestNotifications(LoginUser loginUser) {

        return joinRequestRepository.searchNotifications(loginUser.getUser().getUserId());
    }
}
