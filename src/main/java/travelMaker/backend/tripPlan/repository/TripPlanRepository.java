package travelMaker.backend.tripPlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.tripPlan.model.TripPlan;

import java.util.List;

@Repository
public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {
    List<TripPlan> findByRegion(String region);
}
