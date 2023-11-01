package travelMaker.backend.schedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.schedule.dto.response.DetailsMarker;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.schedule.dto.response.TripPlanDetails;
import travelMaker.backend.schedule.dto.response.TripPlans;
import travelMaker.backend.schedule.model.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.as;
import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
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

        //tripPlans
        List<TripPlans> tripPlans = new ArrayList<>();

        for (LocalDate scheduleDate : scheduleDates) {
            tripPlans.add(
                    TripPlans.builder()
                            .scheduledDate(scheduleDate)
                            .tripPlanDetails(queryFactory
                                    .select(Projections.constructor(TripPlanDetails.class,
                                            tripPlan.tripPlanId,
                                            tripPlan.destinationName,
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
                                    .fetch()
                            )
                            .build());
        }
        return tripPlans;
    }

}
