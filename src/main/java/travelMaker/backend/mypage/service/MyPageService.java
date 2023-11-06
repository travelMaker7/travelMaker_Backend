package travelMaker.backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.mypage.dto.request.RegisterReviewDto;
import travelMaker.backend.mypage.dto.request.UpdateDescriptionDto;
import travelMaker.backend.mypage.dto.request.UpdateNicknameDto;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.dto.response.RegisteredDto;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;
import travelMaker.backend.mypage.repository.BookMarkRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final BookMarkRepository bookMarkRepository;
    @Transactional(readOnly = true)
    public BookMarkPlansDto getBookMarkList(LoginUser loginUser) {

        List<BookMarkPlansDto.BookMarkDto> BookMarkScheduleList = bookMarkRepository.bookMark(loginUser.getUser().getUserId());
        return BookMarkPlansDto.builder()
                .schedules(BookMarkScheduleList)
                .build();
    }

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

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
    public RegisteredDto getRegisterScheduleList(LoginUser loginUser){
        List<RegisteredDto.RegisterScheduleDto> registerScheduleList = scheduleRepository.getRegisterScheduleList(loginUser.getUser().getUserId());
        return RegisteredDto.builder()
                .schedules(registerScheduleList)
                .build();

    }

    @Transactional
    public void registerReview(RegisterReviewDto registerReviewDto, Long scheduleId) {
/*        - 칭찬배지 선택하면 리뷰 대상(host)의 해당 배지 1 증가
          - 만족도 선택하면 매너온도 계산해서 증감 (기준점: 36.5 / -0.2, -0.1, 0, +0.1, +0.2)*/

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new GlobalException(ErrorCode.SCHEDULE_NOT_FOUND));
        Long hostId = schedule.getUser().getUserId();
        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        // 리뷰 대상(host) 엔티티에서 각 항목을 뽑아냄
        Integer photographer = host.getPraiseBadge().getPhotographer();
        Integer timeIsGold = host.getPraiseBadge().getTimeIsGold();
        Integer kingOfKindness = host.getPraiseBadge().getKingOfKindness();
        Integer professionalGuide = host.getPraiseBadge().getProfessionalGuide();
        Double mannerScore = host.getMannerScore();
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
        host.updatePraiseBadge(photographer, timeIsGold, kingOfKindness, professionalGuide);

        mannerScore += registerReviewDto.getMannerScore();

        if (mannerScore < 0) {
            throw new GlobalException(ErrorCode.MANNER_SCORE_MUST_BE_ZERO_OR_HIGHER);
        } else {
            host.updateMannerScore(mannerScore);
        }
    }

    @Transactional
    public void deleteUserByUserId(LoginUser loginUser){
        User user = userRepository.findById(loginUser.getUser().getUserId())
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));
        System.out.println("user = " + user);
            userRepository.delete(user);
    }

    }


