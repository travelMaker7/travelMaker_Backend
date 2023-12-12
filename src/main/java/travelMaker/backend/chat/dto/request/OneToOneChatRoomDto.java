package travelMaker.backend.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OneToOneChatRoomDto {
    private Long targetUserId;
    private String roomName;
}
