package travelMaker.backend.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import travelMaker.backend.user.model.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileDto {

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

    public static MyProfileDto from(User user){
        return MyProfileDto.builder()
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
