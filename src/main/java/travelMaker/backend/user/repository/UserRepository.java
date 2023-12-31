package travelMaker.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelMaker.backend.user.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String email);


    boolean existsByNickname(String nickname);
}
