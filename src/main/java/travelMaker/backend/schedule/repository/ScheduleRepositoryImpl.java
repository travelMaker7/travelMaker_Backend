package travelMaker.backend.schedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.mypage.dto.response.AccompanyTripPlans;
import travelMaker.backend.mypage.dto.response.RegisteredDto;
import travelMaker.backend.schedule.dto.response.DetailsMarker;
import travelMaker.backend.schedule.dto.response.TripPlanDetails;
import travelMaker.backend.schedule.dto.response.TripPlans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static travelMaker.backend.JoinRequest.model.QJoinRequest.joinRequest;
import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;

@RequiredArgsConstructor
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

        //scheduleDates 리스트
        List<LocalDate> scheduleDates = queryFactory
                .select(date.scheduledDate)
                .from(date)
                .where(date.schedule.scheduleId.eq(scheduleId))
                .fetch();

        // tripPlans 리스트
        List<TripPlans> tripPlans = new ArrayList<>();

        for (LocalDate scheduleDate : scheduleDates) {

            List<TripPlanDetails> tripPlanDetails = queryFactory
                    .select(Projections.constructor(TripPlanDetails.class,
                            tripPlan.tripPlanId,
                            tripPlan.destinationName,
                            new CaseBuilder()
                                    .when(tripPlan.joinCnt.goe(tripPlan.wishCnt))
                                    .then(false)
                                    .otherwise(tripPlan.joinCnt.lt(tripPlan.wishCnt))
                                    .as("overWish"),
                            tripPlan.joinCnt,
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
//            joinStatusCondition = schedule.finishDate.lt(LocalDate.now());
            joinStatusCondition = date.scheduledDate.lt(LocalDate.now());
        } else {
            joinStatusCondition = joinRequest.joinStatus.eq(JoinStatus.valueOf(status));
        }

        return queryFactory.select(Projections.constructor(AccompanyTripPlans.AccompanyTripPlan.class,
                        schedule.scheduleId,
                        schedule.scheduleName,
                        date.scheduledDate,
                        tripPlan.arriveTime,
                        tripPlan.leaveTime,
                        user.nickname,
                        tripPlan.region
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
}
