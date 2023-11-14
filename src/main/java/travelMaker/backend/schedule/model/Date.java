package travelMaker.backend.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateId;

    private LocalDate scheduledDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;


    @Builder
    public Date(LocalDate scheduledDate, Schedule schedule) {
        this.scheduledDate = scheduledDate;
        this.schedule = schedule;
    }
}
