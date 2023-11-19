package travelMaker.backend.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.chat.dto.ChatMessageDto;

import java.util.List;
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ChatMessageList {
    private List<ChatMessageDto> messages;
    private long totalPages;
    private long currentPage;


}
