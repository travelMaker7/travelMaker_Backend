package travelMaker.backend.schedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.schedule.dto.response.DetailsMarker;
import travelMaker.backend.schedule.dto.response.TripPlanDetails;
import travelMaker.backend.schedule.dto.response.TripPlans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.tripPlan.model.QTripPlan.*;

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

}
