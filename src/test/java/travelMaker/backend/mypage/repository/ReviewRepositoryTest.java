package travelMaker.backend.mypage.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.mypage.model.Review;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Rollback(value = false)
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void findByTripPlanIdAndReviewerUserIdAndReviewTargetUserId() {
        Review review = reviewRepository.findByTripPlanIdAndReviewerUserIdAndReviewTargetUserId(1l, 2l, 1l);
        log.info("review: " + review.getReviewId());
    }
}