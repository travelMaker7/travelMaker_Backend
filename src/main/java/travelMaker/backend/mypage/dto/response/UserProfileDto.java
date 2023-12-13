package travelMaker.backend.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.user.model.User;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserProfileDto {
    private String nickname;
    private String imageUrl;
    private String userAgeRange;
    private String userGender;
    private String userDescription;
    private Integer photographer;
    private Integer timeIsGold;
    private Integer kingOfKindness;
    private Integer professionalGuide;
    private Double mannerScore;


    public static UserProfileDto from(User user){
        return UserProfileDto.builder()
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .userAgeRange(user.getUserAgeRange())
                .userGender(user.getUserGender())
                .userDescription(user.getUserDescription())
//                .photographer(user.getPraiseBadge().getPhotographer())
//                .timeIsGold(user.getPraiseBadge().getTimeIsGold())
//                .kingOfKindness(user.getPraiseBadge().getKingOfKindness())
//                .professionalGuide(user.getPraiseBadge().getProfessionalGuide())
//                .mannerScore(user.getMannerScore())
                .build();

    }
}
