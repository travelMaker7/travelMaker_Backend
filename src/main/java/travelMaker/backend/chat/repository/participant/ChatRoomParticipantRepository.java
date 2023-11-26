package travelMaker.backend.chat.repository.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travelMaker.backend.chat.model.ChatRoomParticipant;

import java.util.List;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long>, ChatRoomParticipantCustom {

    @Query(
            "SELECT cp FROM ChatRoomParticipant cp WHERE cp.user.userId= :userId"
    )
    List<ChatRoomParticipant> findByUserId(@Param("userId")Long userId);

    @Query(
            "SELECT count(cp) FROM ChatRoomParticipant cp WHERE cp.chatRoom.chatRoomId=:chatRoomId"
    )
    Long findByParticipantCount(@Param("chatRoomId")Long chatRoomId);
}
