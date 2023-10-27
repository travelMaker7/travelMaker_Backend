package travelMaker.backend.tripPlan.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.dto.response.SummaryTripPlan;

import java.util.List;

import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;

@RequiredArgsConstructor
public class TripPlanRepositoryImpl implements TripPlanRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public SearchRegionDto searchTripPlan(String region, String destinationName) {

        List<SummaryTripPlan> summaryTripPlans = queryFactory.select(Projections.constructor(SummaryTripPlan.class,
                        user.userName,
                        date.scheduleDate,
                        tripPlan.arriveTime,
                        tripPlan.leaveTime
                ))
                .from(tripPlan)
                .leftJoin(date).on(tripPlan.date.eq(date))
                .leftJoin(user).on(schedule.user.eq(user))
                .where(
                        tripPlan.destinationName.eq(destinationName),
                        tripPlan.region.eq(region)
                ).fetch();


        List<String> addressList = queryFactory.select(tripPlan.address)
                .from(tripPlan)
                .where(
                        tripPlan.destinationName.eq(destinationName),
                        tripPlan.region.eq(region)
                ).fetch();
        String address = addressList.get(0);


        return SearchRegionDto.builder()
                    .address(address)
                    .destinationName(destinationName)
                    .schedules(summaryTripPlans)
                    .build();

    }
}
