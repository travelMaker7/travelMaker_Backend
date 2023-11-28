package travelMaker.backend.JoinRequest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.JoinRequest.model.Notifications;

import java.util.List;

@SpringBootTest
@Rollback(value = false)
@Transactional
class NotificationsRepositoryTest {

    @Autowired
    NotificationsRepository notificationsRepository;

    @Test
    void findByUserId() {
        List<Notifications> byUserId = notificationsRepository.findByUserId(1l);
        for (Notifications notifications : byUserId) {
            System.out.println("notifications = " + notifications);
        }
    }

//    @Test
//    public void test() throws Exception{
//        //given
//        Notifications byJoinId = notificationsRepository.findByJoinId(5L);
//        //when
//        System.out.println(byJoinId);
//        //then
//    }

}