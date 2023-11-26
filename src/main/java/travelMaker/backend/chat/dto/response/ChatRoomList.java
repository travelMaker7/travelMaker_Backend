package travelMaker.backend.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.chat.dto.ChatRoomDto;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ChatRoomList {
    private List<ChatRoomPreviewDto> chatRooms;
}
