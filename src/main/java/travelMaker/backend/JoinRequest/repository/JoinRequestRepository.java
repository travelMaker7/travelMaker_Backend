package travelMaker.backend.JoinRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelMaker.backend.JoinRequest.model.JoinRequest;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long>, JoinRequestRepositoryCustom {
    @Query("SELECT jr FROM JoinRequest jr WHERE jr.tripPlan.tripPlanId = :tripPlanId AND jr.user.userId = :userId")
    JoinRequest findByTripPlanIdAndUserId(@Param("tripPlanId") Long tripPlanId, @Param("userId") Long userId);

}
