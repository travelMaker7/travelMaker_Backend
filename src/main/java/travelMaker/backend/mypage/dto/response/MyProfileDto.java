package travelMaker.backend.mypage.dto.response;

import lombok.*;
import travelMaker.backend.mypage.model.Review;
import travelMaker.backend.user.model.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

//    public static MyProfileDto from(User user, Review review){
//        return MyProfileDto.builder()
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
