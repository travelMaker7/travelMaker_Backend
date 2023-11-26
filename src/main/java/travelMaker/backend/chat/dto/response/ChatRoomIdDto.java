package travelMaker.backend.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ChatRoomIdDto {
    private String redisRoomId;
    private Long chatRoomId;
}
