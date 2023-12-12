package travelMaker.backend.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomEnterInfoDto {
    private Long userId;

    public ChatRoomEnterInfoDto(Long userId) {
        this.userId = userId;
    }
}
