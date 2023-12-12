package travelMaker.backend.chat.repository.participant;

import travelMaker.backend.chat.dto.response.ChatRoomPreviewDto;
import travelMaker.backend.chat.model.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomParticipantCustom {
    Optional<ChatRoom> existsParticipantChatRoom(Long chatRoomId, Long userId);
}
