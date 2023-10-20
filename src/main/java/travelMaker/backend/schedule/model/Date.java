package travelMaker.backend.schedule.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateId;
    private LocalDate dateNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
}
