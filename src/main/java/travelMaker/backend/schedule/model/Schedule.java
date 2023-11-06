package travelMaker.backend.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.user.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Date> dates = new ArrayList<>();


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

    public void addUser(User user) {
//        if(this.user != null){
//            user.getSchedules().remove(this);
//        }
        this.user = user;
//        user.getSchedules().add(this);
    }
}
