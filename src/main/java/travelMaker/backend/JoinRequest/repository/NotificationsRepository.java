package travelMaker.backend.JoinRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelMaker.backend.JoinRequest.model.Notifications;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    @Query("SELECT n FROM Notifications n WHERE n.user.userId = :userId")
    List<Notifications> findByUserId(@Param("userId") Long userId);
}
