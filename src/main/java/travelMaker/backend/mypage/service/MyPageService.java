package travelMaker.backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.mypage.dto.request.UpdateDescriptionDto;
import travelMaker.backend.mypage.dto.request.UpdateNicknameDto;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.schedule.repository.ScheduleRepository;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

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

    @Transactional
    public void deleteUserByUserId(LoginUser loginUser){
        User user = userRepository.findById(loginUser.getUser().getUserId())
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));
        System.out.println("user = " + user);
            userRepository.delete(user);
    }

}
