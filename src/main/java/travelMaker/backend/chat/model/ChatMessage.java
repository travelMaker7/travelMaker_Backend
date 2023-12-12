package travelMaker.backend.chat.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import travelMaker.backend.chat.repository.ChatRoomRepository;
import travelMaker.backend.user.model.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender; // 발신자

    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    // 나중에 사용하자~
//    private boolean isChecked; // 필요없지 않나
//    @ManyToOne
//    @JoinColumn(name = "receiverId")
//    private User receiver; // 수신자

    @Builder
    public ChatMessage(Long chatId, String message, User sender, LocalDateTime createdAt, ChatRoom chatRoom) {
        this.chatId = chatId;
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
        this.chatRoom = chatRoom;
    }
}


