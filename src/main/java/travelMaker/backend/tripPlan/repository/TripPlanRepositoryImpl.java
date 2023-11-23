package travelMaker.backend.tripPlan.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.dto.response.SummaryTripPlan;

import java.util.ArrayList;
import java.util.List;

import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;


@RequiredArgsConstructor
public class TripPlanRepositoryImpl implements TripPlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public SearchRegionDto findTripPlansByRegionAndCoordinates(String region, Double destinationX, Double destinationY) {

        List<Tuple> results = queryFactory
                .select(
                        user.nickname,
                        schedule.scheduleId,
                        date.scheduledDate,
                        tripPlan.arriveTime,
                        tripPlan.leaveTime,
                        tripPlan.address,
                        tripPlan.destinationName
                )
                .from(tripPlan)
                .leftJoin(date).on(tripPlan.date.eq(date))
                .leftJoin(user).on(schedule.user.eq(user))
                .where(
                        tripPlan.wishJoin.eq(true),
                        tripPlan.region.eq(region),
                        tripPlan.destinationX.eq(destinationX),
                        tripPlan.destinationY.eq(destinationY)
                )
                .fetch();

        List<SummaryTripPlan> summaryTripPlans = new ArrayList<>();
        String address = null;
        String destinationName = null;

        for (Tuple tuple : results) {
            if (address == null) {
                address = tuple.get(tripPlan.address);
                destinationName = tuple.get(tripPlan.destinationName);
            }

            SummaryTripPlan summaryTripPlan = new SummaryTripPlan(
                    tuple.get(user.nickname),
                    tuple.get(schedule.scheduleId),
                    tuple.get(date.scheduledDate),
                    tuple.get(tripPlan.arriveTime),
                    tuple.get(tripPlan.leaveTime)
            );
            summaryTripPlans.add(summaryTripPlan);
        }

        return SearchRegionDto.builder()
                .address(address)
                .destinationName(destinationName)
                .schedules(summaryTripPlans)
                .build();
    }
}
