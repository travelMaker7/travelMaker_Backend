package travelMaker.backend.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ChatRoomDto implements Serializable {
    private String redisRoomId;
    public static ChatRoomDto createChatRoom() {
        return ChatRoomDto.builder()
                .redisRoomId(UUID.randomUUID().toString())
                .build();
    }
}
