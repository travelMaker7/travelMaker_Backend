package travelMaker.backend.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import travelMaker.backend.schedule.dto.response.ScheduleDetailsDto;
import travelMaker.backend.user.model.User;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE schedule SET is_deleted = true WHERE schedule_id = ?")
public class Schedule{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String scheduleName;

    private String scheduleDescription;

    @Column(nullable = false)
    private String chatUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private boolean isDeleted;

    @Builder
    public Schedule(String scheduleName, String scheduleDescription, String chatUrl, User user, boolean isDeleted) {
        this.scheduleName = scheduleName;
        this.scheduleDescription = scheduleDescription;
        this.chatUrl = chatUrl;
        this.user = user;
        this.isDeleted = isDeleted;
    }

}
