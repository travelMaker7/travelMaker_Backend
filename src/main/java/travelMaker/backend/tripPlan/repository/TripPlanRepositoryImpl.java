package travelMaker.backend.tripPlan.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import travelMaker.backend.tripPlan.dto.request.SearchRequest;
import travelMaker.backend.tripPlan.dto.response.SearchRegionDto;
import travelMaker.backend.tripPlan.dto.response.SummaryTripPlan;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;


@RequiredArgsConstructor
public class TripPlanRepositoryImpl implements TripPlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<TripPlan> findTripPlansByConditions(SearchRequest searchRequest) {
        JPAQuery<TripPlan> results = queryFactory
                .select (tripPlan)
                .from(tripPlan)
                .leftJoin(date).on(tripPlan.date.eq(date))
                .leftJoin(user).on(schedule.user.eq(user))
                .where(
                        tripPlan.wishJoin.eq(true),
                        betweenPerson(searchRequest.getMinPerson(), searchRequest.getMaxPerson()),
                        targetDate(searchRequest.getTargetStartDate(), searchRequest.getTargetFinishDate()),
                        targetRegion(searchRequest.getRegion()),
                        targetGender(searchRequest.getGender()),
                        targetAgeRange(searchRequest.getAgeRange())
                );

        List<TripPlan> searchedresults = results.fetch();
        List<TripPlan> getResults = new ArrayList<>();
        for (TripPlan tripPlan1 : searchedresults) {
            getResults.add(tripPlan1);
        }

        return getResults;

    }

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
    @Override
    public SearchRegionDto searchTripPlan(SearchRequest searchRequest, Double destinationX, Double destinationY){
        JPAQuery<Tuple> query= queryFactory
                .select(user.nickname,
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
                        tripPlan.destinationX.eq(destinationX),
                        tripPlan.destinationY.eq(destinationY),
                        betweenPerson(searchRequest.getMinPerson(), searchRequest.getMaxPerson()),
                        targetDate(searchRequest.getTargetStartDate(), searchRequest.getTargetFinishDate()),
                        targetRegion(searchRequest.getRegion()),
                        targetGender(searchRequest.getGender()),
                        targetAgeRange(searchRequest.getAgeRange())
                );
        List<Tuple> results = query.fetch();
        List<SummaryTripPlan> searchedResults = new ArrayList<>();
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
            searchedResults.add(summaryTripPlan);
        }
        return SearchRegionDto.builder()
                .address(address)
                .destinationName(destinationName)
                .schedules(searchedResults)
                .build();
    }
    public BooleanExpression betweenPerson(Integer minPerson, Integer maxPerson){
        BooleanExpression isMinPerson = isEmpty(minPerson) ? null : tripPlan.wishCnt.goe(minPerson);
        BooleanExpression isMaxPerson = isEmpty(maxPerson) ? null : tripPlan.wishCnt.loe(maxPerson);
        return Expressions.allOf(isMinPerson, isMaxPerson);
    }
    public BooleanExpression targetDate(LocalDate targetStartDate, LocalDate targetFinishDate){
        BooleanExpression isAfterStartDate = isEmpty(targetStartDate) ? null : tripPlan.date.scheduledDate.goe(targetStartDate);
        BooleanExpression isBeforeFinishDate = isEmpty(targetFinishDate) ? null : tripPlan.date.scheduledDate.loe(targetFinishDate);
        return Expressions.allOf(isAfterStartDate, isBeforeFinishDate);
    }
    private BooleanExpression targetRegion(String region){
        return isEmpty(region) ? null : tripPlan.region.eq(region);
    }
    private BooleanExpression targetGender(String gender){
        return isEmpty(gender) ? null : schedule.user.userGender.eq(gender);
    }
    private BooleanExpression targetAgeRange(String ageRange){
        return isEmpty(ageRange) ? null : schedule.user.userAgeRange.eq(ageRange);
    }
}
