package travelMaker.backend.mypage.dto.response;

import lombok.*;
import travelMaker.backend.mypage.model.Review;
import travelMaker.backend.user.model.User;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
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



//    public static UserProfileDto from(User user){
//        return UserProfileDto.builder()
//                .nickname(user.getNickname())
//                .imageUrl(user.getImageUrl())
//                .userAgeRange(user.getUserAgeRange())
//                .userGender(user.getUserGender())
//                .userDescription(user.getUserDescription())
//                .photographer(review.getPraiseBadge().getPhotographer())
//                .timeIsGold(review.getPraiseBadge().getTimeIsGold())
//                .kingOfKindness(review.getPraiseBadge().getKingOfKindness())
//                .professionalGuide(review.getPraiseBadge().getProfessionalGuide())
//                .mannerScore(review.getMannerScore())
//                .build();
//    }


}
