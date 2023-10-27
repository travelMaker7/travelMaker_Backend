package travelMaker.backend.schedule.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.user.model.User;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Schedule{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String scheduleName;

    private String scheduleDescription;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate finishDate;

    @Column(nullable = false)
    private String chatUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Schedule(
            String scheduleName,
            String scheduleDescription,
            LocalDate startDate,
            LocalDate finishDate,
            String chatUrl,
            User user
    ) {
        this.scheduleName = scheduleName;
        this.scheduleDescription = scheduleDescription;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.chatUrl = chatUrl;
        this.user = user;
    }
}
