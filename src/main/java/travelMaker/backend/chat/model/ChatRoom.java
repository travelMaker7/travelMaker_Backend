package travelMaker.backend.chat.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.user.model.User;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    private String roomName;
    private String redisRoomId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripPlanId")
    private TripPlan tripPlan; // 1:1채팅방과 1:n을 구분해줄 용도


    // 메시지 조회시 사용하면 편리하지 않을까? 해서 일단 남겨놓음
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessage;


    @Builder
    public ChatRoom(Long chatRoomId, String roomName, String redisRoomId, TripPlan tripPlan) {
        this.chatRoomId = chatRoomId;
        this.roomName = roomName;
        this.redisRoomId = redisRoomId;
        this.tripPlan = tripPlan;
    }
}
