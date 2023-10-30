package travelMaker.backend.JoinRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.JoinRequest.model.JoinRequest;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long>, JoinRequestRepositoryCustom {
}
