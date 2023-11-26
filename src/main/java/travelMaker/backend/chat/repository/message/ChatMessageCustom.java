package travelMaker.backend.chat.repository.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import travelMaker.backend.chat.dto.response.ChatRoomPreviewDto;
import travelMaker.backend.chat.model.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageCustom {
    Page<ChatMessage> findTop100ByChatRoomIdOrderByCreatedAt(Long chatRoomId, Pageable pageable);
    Optional<ChatMessage> getLatestMessageByChatRoomId(Long chatRoomId);
}
