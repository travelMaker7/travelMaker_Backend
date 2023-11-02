package travelMaker.backend.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.schedule.model.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
}
