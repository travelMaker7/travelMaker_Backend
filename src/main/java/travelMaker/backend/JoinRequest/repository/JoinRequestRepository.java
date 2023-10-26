package travelMaker.backend.JoinRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelMaker.backend.JoinRequest.model.JoinRequest;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
}
