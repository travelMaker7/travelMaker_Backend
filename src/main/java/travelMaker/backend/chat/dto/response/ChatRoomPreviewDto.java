package travelMaker.backend.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class ChatRoomPreviewDto {
    private String roomName;
    private Long chatRoomId;
    private String redisRoomId;
    private Long participants; // chatroom에 있는 참여자
    private String recentTalk; // message
    private LocalDateTime recentTalkDate; // message
//    private int notReadCnt; // 나중에 구현

    @Builder

    public ChatRoomPreviewDto(String roomName, Long chatRoomId, String redisRoomId, Long participants, String recentTalk, LocalDateTime recentTalkDate) {
        this.roomName = roomName;
        this.chatRoomId = chatRoomId;
        this.redisRoomId = redisRoomId;
        this.participants = participants;
        this.recentTalk = recentTalk;
        this.recentTalkDate = recentTalkDate;
    }
}
