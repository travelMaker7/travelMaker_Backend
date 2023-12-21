package travelMaker.backend.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.schedule.model.Date;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    List<Date> findByScheduleScheduleIdIn(List<Long> scheduleIds);

    List<Date> findByScheduleScheduleId(Long scheduleId);
}
