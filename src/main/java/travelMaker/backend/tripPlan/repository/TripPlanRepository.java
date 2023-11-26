package travelMaker.backend.tripPlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.List;

public interface TripPlanRepository extends JpaRepository<TripPlan, Long>, TripPlanRepositoryCustom {
 List<TripPlan> findByRegion(String region);
 List<TripPlan> findByDateDateIdIn(List<Long> ids);
}
