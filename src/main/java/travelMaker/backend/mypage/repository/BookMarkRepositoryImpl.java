package travelMaker.backend.mypage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;
import travelMaker.backend.mypage.model.QBookMark;
import travelMaker.backend.schedule.model.QDate;
import travelMaker.backend.tripPlan.model.QTripPlan;

import java.util.List;

import static travelMaker.backend.mypage.model.QBookMark.bookMark;
import static travelMaker.backend.schedule.model.QDate.date;
import static travelMaker.backend.schedule.model.QSchedule.schedule;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;

@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<BookMarkPlansDto.BookMarkDto> bookMark(Long userId) {


        return queryFactory.selectDistinct(Projections.constructor(BookMarkPlansDto.BookMarkDto.class,
                schedule.scheduleId,
                schedule.scheduleName,
                schedule.scheduleDescription,
                schedule.user.nickname,
                schedule.startDate,
                schedule.finishDate
        ))
                .from(schedule)
                .join(bookMark).on(schedule.eq(bookMark.schedule))
                .join(user).on(bookMark.user.userId.eq(userId))
                .fetch();
    }
}
