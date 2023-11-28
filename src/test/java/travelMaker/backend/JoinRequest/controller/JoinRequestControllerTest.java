package travelMaker.backend.JoinRequest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.user.login.LoginUser;
import travelMaker.backend.user.model.User;

@SpringBootTest
@Rollback(value = false)
public class JoinRequestControllerTest {

    @Autowired
    JoinRequestController joinRequestController;

    @Test
    public void 동행신청알림조회 () throws Exception{

        User user = User.builder()
                .userId(1l)
                .build();

        joinRequestController.JoinRequestNotification(new LoginUser(user));
        
    }
}
