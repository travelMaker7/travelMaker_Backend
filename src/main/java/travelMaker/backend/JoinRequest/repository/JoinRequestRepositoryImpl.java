package travelMaker.backend.JoinRequest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import travelMaker.backend.JoinRequest.dto.response.JoinRequestNotification;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;

import java.util.List;

import static travelMaker.backend.JoinRequest.model.QNotifications.notifications;

@Slf4j
@RequiredArgsConstructor
public class JoinRequestRepositoryImpl implements JoinRequestRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public NotificationsDto searchNotifications(Long userId) {

        List<JoinRequestNotification> notificationList = queryFactory
                .select(Projections.constructor(JoinRequestNotification.class,
                        notifications.joinId,
                        notifications.scheduleName,
                        notifications.destinationName,
                        notifications.nickname,
                        notifications.joinStatus))
                .from(notifications)
                .where(notifications.user.userId.eq(userId))
                .fetch();

        return NotificationsDto.builder()
                .notifications(notificationList)
                .build();

//        QUser guest = new QUser("guest");
//        QUser host = new QUser("host");
//
//        List<JoinRequestNotification> notifications = queryFactory
//                .select(Projections.constructor(JoinRequestNotification.class,
//                        joinRequest.joinId,
//                        schedule.scheduleName,
//                        tripPlan.destinationName,
//                        guest.nickname,
//                        joinRequest.joinStatus))
//                .from(joinRequest, tripPlan, date, schedule, guest, host)
//                .where(
//                        joinRequest.user.userId.eq(guest.userId),
//                        joinRequest.tripPlan.tripPlanId.eq(tripPlan.tripPlanId),
//                        tripPlan.date.dateId.eq(date.dateId),
//                        date.schedule.scheduleId.eq(schedule.scheduleId),
//                        schedule.user.userId.eq(host.userId),
//                        joinRequest.joinStatus.eq(JoinStatus.승인대기),
//                        schedule.user.userId.eq(userId)
//                )
//                .fetch();
//
//        return NotificationsDto.builder()
//                .notifications(notifications)
//                .build();

    }
}
