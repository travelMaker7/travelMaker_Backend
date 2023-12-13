package travelMaker.backend.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.user.login.LoginUser;

import static travelMaker.backend.mypage.model.QReview.review;
import static travelMaker.backend.user.model.QUser.user;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MyProfileDto getMyProfile(LoginUser loginUser) {
        return queryFactory.select(Projections.constructor(MyProfileDto.class,
                        user.nickname,
                        user.imageUrl,
                        user.userAgeRange,
                        user.userGender,
                        user.userDescription,
                        review.praiseBadge.photographer.sum(),
                        review.praiseBadge.timeIsGold.sum(),
                        review.praiseBadge.kingOfKindness.sum(),
                        review.praiseBadge.professionalGuide.sum(),
                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0) + 36.5", review.mannerScore.sum())
                ))
                .from(user)
                .leftJoin(review).on(user.eq(review.reviewTarget).and(review.reviewTarget.eq(loginUser.getUser()))) // 리뷰가 없는 user도 결과에 포함되도록 leftJoin
                .groupBy(user.nickname, user.imageUrl, user.userAgeRange, user.userGender, user.userDescription)
                .fetchOne();
    }

    @Override
    public UserProfileDto getUserProfile(Long targetUserId, LoginUser loginUser) {
        return queryFactory.select(Projections.constructor(UserProfileDto.class,
                        user.nickname,
                        user.imageUrl,
                        user.userAgeRange,
                        user.userGender,
                        user.userDescription,
                        review.praiseBadge.photographer.sum(),
                        review.praiseBadge.timeIsGold.sum(),
                        review.praiseBadge.kingOfKindness.sum(),
                        review.praiseBadge.professionalGuide.sum(),
                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0) + 36.5", review.mannerScore.sum())
                ))
                .from(user)
                .leftJoin(review).on(user.eq(review.reviewTarget).and(review.reviewTarget.userId.eq(targetUserId))) // 리뷰가 없는 user도 결과에 포함되도록 leftJoin
                .groupBy(user.nickname, user.imageUrl, user.userAgeRange, user.userGender, user.userDescription)
                .fetchOne();
    }
}
