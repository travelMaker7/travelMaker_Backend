package travelMaker.backend.tripPlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelMaker.backend.schedule.model.Date;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.List;

@Repository
public interface TripPlanRepository extends JpaRepository<TripPlan, Long>, TripPlanRepositoryCustom {
    List<TripPlan> findByRegion(String region);

}
