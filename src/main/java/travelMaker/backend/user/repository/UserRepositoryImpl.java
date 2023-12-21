package travelMaker.backend.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import travelMaker.backend.mypage.dto.response.MyProfileDto;
import travelMaker.backend.mypage.dto.response.UserProfileDto;
import travelMaker.backend.user.login.LoginUser;

import java.util.List;

import static travelMaker.backend.mypage.model.QReview.review;
import static travelMaker.backend.user.model.QUser.user;

@Slf4j
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
                        review.photographer.sum(),
                        review.timeIsGold.sum(),
                        review.kingOfKindness.sum(),
                        review.professionalGuide.sum(),
                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0) + 36.5", review.mannerScore.sum())
                ))
                .from(user)
                .leftJoin(review).on(review.reviewTarget.eq(loginUser.getUser()))
                .where(
                        user.eq(loginUser.getUser())
                )
                .groupBy(user.userId)
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
                        review.photographer.sum(),
                        review.timeIsGold.sum(),
                        review.kingOfKindness.sum(),
                        review.professionalGuide.sum(),
                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0) + 36.5", review.mannerScore.sum())
                ))
                .from(user)
                .leftJoin(review).on(review.reviewTarget.eq(loginUser.getUser()))
                .where(
                        user.eq(loginUser.getUser())
                )
                .groupBy(user.userId)
                .fetchOne();
    }
}
