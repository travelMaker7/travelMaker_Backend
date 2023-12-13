package travelMaker.backend.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.mypage.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByTripPlanIdAndReviewerUserIdAndReviewTargetUserId(Long tripPlanId, Long reviewerId, Long reviewTargetId);
    boolean existsByTripPlanIdAndReviewerUserIdAndReviewTargetUserId(Long tripPlanId, Long reviewerId, Long reviewTargetId);
}
