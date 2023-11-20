package travelMaker.backend.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import travelMaker.backend.chat.model.ChatMessage;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class ChatMessageDto {

    private MessageType messageType;
    private String redisRoomId;
    private Long chatRoomId;
    private Long senderId;
    private String nickname;
    private String message;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    @Builder
    public ChatMessageDto(MessageType messageType, String redisRoomId, Long chatRoomId, Long senderId, String nickname, String message, LocalDateTime createdAt) {
        this.messageType = messageType;
        this.redisRoomId = redisRoomId;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.nickname = nickname;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    // 대화 조회
    public static ChatMessageDto from(ChatMessage chatMessage, String redisRoomId, Long chatRoomId){
        return ChatMessageDto.builder()
                .senderId(chatMessage.getSender().getUserId())
                .nickname(chatMessage.getSender().getNickname())
                .redisRoomId(redisRoomId)
                .chatRoomId(chatRoomId)
                .createdAt(chatMessage.getCreatedAt())
                .message(chatMessage.getMessage())
                .build();
    }
}
