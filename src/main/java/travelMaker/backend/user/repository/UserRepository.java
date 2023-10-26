package travelMaker.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
