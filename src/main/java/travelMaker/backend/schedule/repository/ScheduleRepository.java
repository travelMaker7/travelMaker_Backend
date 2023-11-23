package travelMaker.backend.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import travelMaker.backend.schedule.model.Schedule;

import java.util.List;

@Repository
public interface  ScheduleRepository extends JpaRepository<Schedule, Long> , ScheduleRepositoryCustom {

    List<Schedule> findAllByUserUserId(Long userId);
}
