package travelMaker.backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.mypage.dto.request.UpdateProfileDto;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    @Transactional
    public void updateProfile(UpdateProfileDto updateProfileDto, LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getUser().getUserId()).orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND));
        if(updateProfileDto.getUserDescription() != null && !updateProfileDto.getUserDescription().isBlank()){
            user.updateDescription(updateProfileDto.getUserDescription());
        }
        if(updateProfileDto.getNickname() != null && !updateProfileDto.getNickname().isBlank()){
            user.updateNickname(updateProfileDto.getNickname());
        }
        userRepository.save(user);
    }
}
