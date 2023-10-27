package travelMaker.backend.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelMaker.backend.schedule.model.Date;

public interface DateRepository extends JpaRepository<Date, Long> {
}
