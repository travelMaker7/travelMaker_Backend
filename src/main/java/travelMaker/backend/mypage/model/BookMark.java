package travelMaker.backend.mypage.model;

import jakarta.persistence.*;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.user.model.User;

@Entity
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
