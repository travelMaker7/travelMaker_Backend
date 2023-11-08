package travelMaker.backend.mypage.dto.response;

import lombok.*;
import travelMaker.backend.user.model.User;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
@ToString
public class JoinUsers {
    private List<JoinUserProfileInfo> joinUsers;

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    @ToString
    public static class JoinUserProfileInfo{
        private Long userId;
        private String username;
        private String imageUrl;


        public static JoinUserProfileInfo from(User user){
            return JoinUserProfileInfo.builder()
                    .userId(user.getUserId())
                    .username(user.getUserName())
                    .imageUrl(user.getImageUrl())
                    .build();
        }
    }
}
