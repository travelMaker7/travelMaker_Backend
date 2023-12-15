package travelMaker.backend.user.repository;

import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.user.login.LoginUser;

public interface UserRepositoryCustom {
    MyProfileDto getMyProfile(LoginUser loginUser);
    UserProfileDto getUserProfile(Long targetUserId, LoginUser loginUser);
}
