package travelMaker.backend.mypage.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.schedule.model.Schedule;
import travelMaker.backend.user.model.User;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    @Builder
    public BookMark(Long id, Schedule schedule, User user) {
        this.id = id;
        this.schedule = schedule;
        this.user = user;
    }

}
