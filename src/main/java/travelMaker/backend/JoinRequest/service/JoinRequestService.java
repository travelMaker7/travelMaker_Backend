package travelMaker.backend.JoinRequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.JoinRequest.dto.request.GuestJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.request.HostJoinRequestDto;
import travelMaker.backend.JoinRequest.dto.response.JoinRequestNotification;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;
import travelMaker.backend.JoinRequest.model.JoinRequest;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.JoinRequest.model.Notifications;
import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;
import travelMaker.backend.JoinRequest.repository.NotificationsRepository;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.schedule.model.Date;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.DateRepository;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final TripPlanRepository tripPlanRepository;
    private final UserRepository userRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final NotificationsRepository notificationsRepository;
    private final DateRepository dateRepository;
    private final ScheduleRepository scheduleRepository;

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
            User guest = userRepository.findById(guestId)
                    .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

            JoinRequest guestJoinRequest = JoinRequest.builder()
                    .tripPlan(tripPlan)
                    .joinStatus(joinStatus)
                    .user(guest)
                    .build();

            // JoinRequest 엔티티 저장
            JoinRequest save = joinRequestRepository.save(guestJoinRequest);

            // NotificationsDto로 NotificationsRepository에도 데이터 저장!
            // 이렇게 동행신청에서 한 번 조회쿼리를 날려서 저장해두면 이후에 for문 돌려서 비교할 필요가 없음!
            Date date = dateRepository.findById(tripPlan.getDate().getDateId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.DATE_NOT_FOUND));
            Schedule schedule = scheduleRepository.findById(date.getSchedule().getScheduleId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
            User host = userRepository.findById(schedule.getScheduleId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

            Notifications notifications = Notifications.builder()
                    .user(host)
                    .joinId(save.getJoinId())
                    .scheduleName(schedule.getScheduleName())
                    .destinationName(tripPlan.getDestinationName())
                    .nickname(guest.getNickname())
                    .joinStatus(joinStatus)
                    .build();

            notificationsRepository.save(notifications);
        }
    }

    @Transactional(readOnly = true)
    public NotificationsDto joinRequestNotifications(LoginUser loginUser) {
        return joinRequestRepository.searchNotifications(loginUser.getUser().getUserId());
    }

    @Transactional
    public void guestJoinCancel(Long tripPlanId, LoginUser loginUser) {

        Long userId = loginUser.getUser().getUserId();

        JoinRequest joinRequest = joinRequestRepository
                .findByTripPlanIdAndUserId(tripPlanId, userId);

        joinRequestRepository.delete(joinRequest);
    }

    @Transactional
    public void hostJoinRequest(HostJoinRequestDto hostJoinRequestDto) {

        Long joinId = hostJoinRequestDto.getJoinId();
        JoinStatus joinStatus = hostJoinRequestDto.getJoinStatus();
        JoinRequest joinRequest = joinRequestRepository.findById(joinId)
                .orElseThrow(() -> new GlobalException(ErrorCode.JOIN_REQUEST_NOT_FOUND));

        if (joinStatus == JoinStatus.신청수락) {

            if (hostJoinRequestDto.isOverWish() == true) {
                throw new GlobalException(ErrorCode.JOIN_CNT_EXCEEDS_WISH_CNT);
            } else {
                joinRequest.updateJoinStatus(joinStatus);
//                joinRequestRepository.save(joinRequest);
            }
        }
        if (joinStatus == JoinStatus.신청거절) {
            joinRequest.updateJoinStatus(joinStatus);
//            joinRequestRepository.save(joinRequest);
        }

    }

}
