package travelMaker.backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.JoinRequest.repository.JoinRequestRepository;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.mypage.dto.request.RegisterReviewDto;
import travelMaker.backend.mypage.dto.request.UpdateDescriptionDto;
import travelMaker.backend.mypage.dto.request.UpdateNicknameDto;
import travelMaker.backend.mypage.dto.response.*;
import travelMaker.backend.mypage.model.BookMark;
import travelMaker.backend.schedule.model.Date;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.DateRepository;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import travelMaker.backend.mypage.repository.BookMarkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final BookMarkRepository bookMarkRepository;
    private final TripPlanRepository tripPlanRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final DateRepository dateRepository;
    @Transactional(readOnly = true)
    public BookMarkPlansDto getBookMarkList(LoginUser loginUser) {

        List<BookMarkPlansDto.BookMarkDto> BookMarkScheduleList = bookMarkRepository.bookMark(loginUser.getUser().getUserId());
        return BookMarkPlansDto.builder()
                .schedules(BookMarkScheduleList)
                .build();
    }

    @Transactional(readOnly = true)
    public MyProfileDto getMyProfile(LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getUser().getUserId()).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        log.info("user = " + user);
        return MyProfileDto.from(user);
    }


    @Transactional(readOnly = true)
    public AccompanyTripPlans getAccompanyListDependingOnStatus(String status, LoginUser loginUser) {

        List<AccompanyTripPlans.AccompanyTripPlan> accompanyScheduleList = scheduleRepository.getAccompanyScheduleList(status, loginUser.getUser().getUserId());
        return AccompanyTripPlans.builder()
                .schedules(accompanyScheduleList)
                .build();
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long targetUserId, LoginUser loginUser) {

        if(loginUser != null){
            if(loginUser.getUser().getUserId().equals(targetUserId))
                throw new GlobalException(ErrorCode.USER_BAD_REQUEST);
        }

        User user = userRepository.findById(targetUserId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        return UserProfileDto.from(user);
    }

    @Transactional
    public void updateProfileDescription(UpdateDescriptionDto updateDescriptionDto, LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getUser().getUserId())
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));

            user.updateDescription(updateDescriptionDto.getUserDescription());
    }

    @Transactional
    public void updateProfileNickname(UpdateNicknameDto updateNicknameDto, LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getUser().getUserId())
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));
            user.updateNickname(updateNicknameDto.getNickname());
    }
    @Transactional (readOnly = true)
    public RegisteredScheduleListDto getRegisterScheduleList(LoginUser loginUser){
        List<Schedule> schedules = scheduleRepository.findAllByUserUserId(loginUser.getUser().getUserId());
        List<Long> scheduleIds = schedules.stream().map(Schedule::getScheduleId).toList();

        List<Date> dates = dateRepository.findByScheduleScheduleIdIn(scheduleIds);
        List<Long> dateIds = dates.stream().map(Date::getDateId).toList();

        Map<Long, List<TripPlan>> collect = tripPlanRepository.findByDateDateIdIn(dateIds).stream()
                .collect(Collectors.groupingBy(trip -> trip.getDate().getSchedule().getScheduleId()));

        List<RegisteredScheduleDto> list = schedules.stream().map(
                schedule -> new RegisteredScheduleDto(
                        schedule,
                        convertTripPlanMarker(collect.get(schedule.getScheduleId()))
                )
        ).toList();
        
        return new RegisteredScheduleListDto(list);
    }

    private List<TripPlanMarker> convertTripPlanMarker(List<TripPlan> plans){
        return plans.stream()
                .map(TripPlanMarker::new)
                .toList();
    }

    @Transactional
    public void registerReview(RegisterReviewDto registerReviewDto, Long userId) {
/*        - 칭찬배지 선택하면 리뷰 대상(host)의 해당 배지 1 증가
          - 만족도 선택하면 매너온도 계산해서 증감 (기준점: 36.5 / -0.2, -0.1, 0, +0.1, +0.2)*/

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        Integer photographer = user.getPraiseBadge().getPhotographer();
        Integer timeIsGold = user.getPraiseBadge().getTimeIsGold();
        Integer kingOfKindness = user.getPraiseBadge().getKingOfKindness();
        Integer professionalGuide = user.getPraiseBadge().getProfessionalGuide();
        Double mannerScore = user.getMannerScore();

        if (registerReviewDto.getPhotographer() == 1) {
            photographer += 1;
        }
        if (registerReviewDto.getTimeIsGold() == 1) {
            timeIsGold += 1;
        }
        if (registerReviewDto.getKingOfKindness() == 1) {
            kingOfKindness += 1;
        }
        if (registerReviewDto.getProfessionalGuide() == 1) {
            professionalGuide += 1;
        }

        user.updatePraiseBadge(photographer, timeIsGold, kingOfKindness, professionalGuide);

        mannerScore += registerReviewDto.getMannerScore();

        if (mannerScore < 0) {
            throw new GlobalException(ErrorCode.MANNER_SCORE_MUST_BE_ZERO_OR_HIGHER);
        } else {
            user.updateMannerScore(mannerScore);
        }
    }

    @Transactional
    public void deleteUserByUserId(LoginUser loginUser){
        User user = userRepository.findById(loginUser.getUser().getUserId())
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));
        System.out.println("user = " + user);
            userRepository.delete(user);
    }

    public void bookMarkRegister(Long scheduleId, LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getUser().getUserId())
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        BookMark bookMark = BookMark.builder()
                .schedule(schedule)
                .user(user)
                .build();
        bookMarkRepository.save(bookMark);
    }

    public void bookMarkDelete(Long bookMarkId, LoginUser loginUser) {
        BookMark bookMark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(()-> new GlobalException(ErrorCode.BOOKMARK_NOT_FOUND));
        if(bookMark.getUser().getUserId()==loginUser.getUser().getUserId()){
        bookMarkRepository.delete(bookMark);
        }
        else{
            new GlobalException(ErrorCode.USER_BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public JoinUsers getJoinUserList(Long scheduleId, Long tripPlanId) {


        // 해당 일정이 존재하는지 체크
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        // 해당 여행지가 존재하는지 체크
        TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(() -> new GlobalException(ErrorCode.TRIP_PLAN_NOT_FOUND));

        User hostUser = schedule.getUser();


        List<User> guestUsers = joinRequestRepository.findByTripPlanIdAndJoinStatus(tripPlanId, JoinStatus.신청수락);

        List<JoinUsers.JoinUserProfileInfo> joinUsers = new ArrayList<>();

        // 게시자도 리스트에 담아줘야함
        joinUsers.add(JoinUsers.JoinUserProfileInfo.from(hostUser));

        // 동행자도 리스트에 담아줘야함
        for (User guestUser : guestUsers) {
            joinUsers.add(JoinUsers.JoinUserProfileInfo.from(guestUser));
        }

        return JoinUsers.builder()
                .joinUsers(joinUsers)
                .build();
    }


}


