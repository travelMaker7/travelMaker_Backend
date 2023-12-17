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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

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
        this.createdAt = createdAt;
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
                .messageType(MessageType.TALK)
                .build();
    }
}
