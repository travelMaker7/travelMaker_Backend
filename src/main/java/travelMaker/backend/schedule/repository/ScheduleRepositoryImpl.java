package travelMaker.backend.schedule.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.dto.response.RegisteredDto;
import travelMaker.backend.schedule.dto.response.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static travelMaker.backend.JoinRequest.model.QJoinRequest.joinRequest;
import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;
@Slf4j
@RequiredArgsConstructor
@Where(clause = "is_deleted = false")
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DetailsMarker> markers(Long scheduleId) {
        List<DetailsMarker> markers = queryFactory
                .select(Projections.constructor(DetailsMarker.class,
                        tripPlan.destinationX,
                        tripPlan.destinationY
                ))
                .from(tripPlan, date)
                .where(
                        tripPlan.date.dateId.eq(date.dateId),
                        date.schedule.scheduleId.eq(scheduleId)
                ).fetch();
        return markers;
    }


    public List<TripPlans> tripPlans(Long scheduleId) {
        JPQLQuery<Long> joinCnt = queryFactory.select(joinRequest.count())
                .from(joinRequest)
                .join(tripPlan).on(joinRequest.tripPlan.eq(tripPlan))
                .where(joinRequest.joinStatus.eq(JoinStatus.신청수락));

        //scheduleDates 리스트
        List<LocalDate> scheduleDates = queryFactory
                .select(date.scheduledDate)
                .from(date)
                .where(date.schedule.scheduleId.eq(scheduleId))
                .fetch();
        BooleanExpression overWish = new CaseBuilder()
                .when(tripPlan.wishCnt.goe(joinCnt)) // 동행인원보다 희망인원이 크거나 같을 경우 -> overWish : false
                .then(false)
                .otherwise(tripPlan.wishCnt.lt(joinCnt)) // 동행인원보다 희망인원이 적을 경우 -> overWish : true
                .as("overWish");
        // tripPlans 리스트
        List<TripPlans> tripPlans = new ArrayList<>();

        for (LocalDate scheduleDate : scheduleDates) {

            List<TripPlanDetails> tripPlanDetails = queryFactory
                    .select(Projections.constructor(TripPlanDetails.class,
                            tripPlan.tripPlanId,
                            tripPlan.destinationName,
                            overWish,
                            joinCnt,
                            tripPlan.wishCnt,
                            tripPlan.wishJoin,
                            tripPlan.address,
                            tripPlan.arriveTime,
                            tripPlan.leaveTime
                    ))
                    .from(tripPlan, date)
                    .where(
                            tripPlan.date.dateId.eq(date.dateId),
                            date.schedule.scheduleId.eq(scheduleId),
                            date.scheduledDate.eq(scheduleDate)
                    )
                    .fetch();

            tripPlans.add(
                    TripPlans.builder()
                            .scheduledDate(scheduleDate)
                            .tripPlanDetails(tripPlanDetails)
                            .build());
        }
        return tripPlans;
    }

    @Override
    public List<AccompanyTripPlans.AccompanyTripPlan> getAccompanyScheduleList(String status, Long userId) {
        // status == 승인대기 -> joinRequest가 승인대기인 애들만
        // stauts == 신청수락 -> joinRequest가 신청수락인 애들만
        // stauts == 신청쉬소 -> joinRequest가 신청취소인 애들만
        // stauts == 동행완료 -> schedule.finishDate.lt(LocalDate.now())

        BooleanExpression joinStatusCondition;
        if ("동행완료".equals(status)) {
            joinStatusCondition = date.scheduledDate.lt(LocalDate.now());
        } else {
            joinStatusCondition = joinRequest.joinStatus.eq(JoinStatus.valueOf(status));
        }

        return queryFactory.select(Projections.constructor(AccompanyTripPlans.AccompanyTripPlan.class,
                        schedule.scheduleId,
                        tripPlan.tripPlanId,
                        schedule.scheduleName,
                        date.scheduledDate,
                        tripPlan.arriveTime,
                        tripPlan.leaveTime,
                        user.nickname,
                        tripPlan.region,
                        tripPlan.destinationName
                ))
                .from(schedule, joinRequest, date, schedule, user)
                .where(
                        joinStatusCondition,
                        joinRequest.user.userId.eq(userId),
                        joinRequest.tripPlan.eq(tripPlan),
                        tripPlan.date.eq(date),
                        date.schedule.eq(schedule),
                        schedule.user.eq(user)
                )
                .groupBy(tripPlan.tripPlanId)
                .orderBy(tripPlan.tripPlanId.desc())
                .fetch();

    }
    @Override
    public List<RegisteredDto.RegisterScheduleDto> getRegisterScheduleList(Long userId){
        return queryFactory.select(Projections.constructor(RegisteredDto.RegisterScheduleDto.class,
                schedule.scheduleId,
                schedule.scheduleName,
                schedule.scheduleDescription,
                user.nickname,
                schedule.startDate,
                schedule.finishDate))
                .from(schedule)
                .where(user.userId.eq(userId))
                .join(schedule.user, user)
                .fetch();
    }

    @Override
    public List<DayByTripPlan> getScheduleAndTripPlanDetails(Long scheduleId) {

        JPQLQuery<Long> joinCnt = queryFactory.select(joinRequest.count())
                .from(joinRequest)
                .join(tripPlan).on(joinRequest.tripPlan.eq(tripPlan))
                .where(joinRequest.joinStatus.eq(JoinStatus.신청수락));

        BooleanExpression overWish = new CaseBuilder()
                .when(tripPlan.wishCnt.goe(joinCnt)) // 동행인원보다 희망인원이 크거나 같을 경우 -> overWish : false
                .then(false)
                .otherwise(tripPlan.wishCnt.lt(joinCnt)) // 동행인원보다 희망인원이 적을 경우 -> overWish : true
                .as("overWish");

        // dateId별로도 구분지어 주고 싶음 918ms걸림
//        List<Long> dateIds = queryFactory.select(date.dateId)
//                .from(date)
//                .join(schedule).on(date.schedule.eq(schedule))
//                .where(schedule.scheduleId.eq(scheduleId))
//                .groupBy(date.dateId)
//                .fetch();

        // 980ms 걸림
        JPQLQuery<Long> dateIds  = queryFactory.select(date.dateId)
                .from(date)
                .join(schedule).on(date.schedule.eq(schedule))
                .where(schedule.scheduleId.eq(scheduleId))
                .groupBy(date.dateId);



        return queryFactory.selectFrom(date).distinct()
                .join(tripPlan).on(tripPlan.date.eq(date))
                .join(schedule).on(date.schedule.eq(schedule))
                .where(
                        schedule.scheduleId.eq(scheduleId)
                        .and(date.dateId.in(dateIds))
                        .and(tripPlan.wishJoin.eq(true))
                )
                .orderBy(tripPlan.tripPlanId.asc())
                .transform(groupBy(date.dateId).list(Projections.constructor(
                        DayByTripPlan.class,
                        date.dateId,
                        date.scheduledDate,
                        list(Projections.constructor(DayByTripPlan.TripPlanDetails2.class,
                        tripPlan.tripPlanId,
                        tripPlan.destinationName,
                        overWish,
                        joinCnt,
                        tripPlan.wishCnt,
                        tripPlan.wishJoin,
                        tripPlan.address,
                        tripPlan.arriveTime,
                        tripPlan.leaveTime

                )))));


        // scheduleId 해당하는 tripPlan 가져와야함, 현재는 tripPlan정보만 가져오고 있음
//        List<DayByTripPlan.TripPlanDetails2> details = queryFactory.select(Projections.constructor(DayByTripPlan.TripPlanDetails2.class,
//                        tripPlan.tripPlanId,
//                        tripPlan.destinationName,
//                        overWish,
//                        joinCnt,
//                        tripPlan.wishCnt,
//                        tripPlan.wishJoin,
//                        tripPlan.address,
//                        tripPlan.arriveTime,
//                        tripPlan.leaveTime
//
//                ))
//                .from(tripPlan, date, schedule)
//                .where(
//                        schedule.scheduleId.eq(scheduleId),
//                        date.schedule.eq(schedule),
//                        tripPlan.date.eq(date),
//                        tripPlan.wishJoin.eq(true)
//                )
//                .groupBy(tripPlan.tripPlanId)
//                .fetch();
//
//
//        Tuple result = queryFactory.select(date.dateId, date.scheduledDate)
//                .from(date)
//                .join(tripPlan).on(tripPlan.date.eq(date))
//                .where(
//                        schedule.scheduleId.eq(scheduleId),
//                        date.schedule.eq(schedule),
//                        tripPlan.date.eq(date),
//                        tripPlan.wishJoin.eq(true)
//
//                )
//                .groupBy(date.dateId)
//                .fetchOne();



        // scheduleId = 4인 date는 4, 13, 52, 55, 57, 50, 79, 93
        // dateId = 4, 13이 존재
        // tripPlanId = 9, 16, 22, 30, 33, 46, 64, 72, 94 -> 9건


    }


}
