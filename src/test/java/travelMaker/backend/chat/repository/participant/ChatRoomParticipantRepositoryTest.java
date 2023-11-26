package travelMaker.backend.chat.repository.participant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import travelMaker.backend.chat.dto.response.ChatRoomPreviewDto;
import travelMaker.backend.chat.model.ChatRoom;
import travelMaker.backend.chat.model.ChatRoomParticipant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChatRoomParticipantRepositoryTest {
    @Autowired
    ChatRoomParticipantRepository chatRoomParticipantRepository;

    @Test
    @DisplayName("유저가 포함된 채팅방 정보 가져오기")
    public void  getChatRoomInfo() throws Exception{
        //given
        List<ChatRoomParticipant> ChatRoomParticipants = chatRoomParticipantRepository.findByUserId(101L);
        //when
        //then
        for (ChatRoomParticipant ChatRoomParticipant : ChatRoomParticipants) {
            System.out.println("결과 : "+ChatRoomParticipant.getChatRoom().getChatRoomId());
            System.out.println("결과 : "+ChatRoomParticipant.getUser().getUserId());
        }

    }

    @Test
    @DisplayName("채팅방에 함께있는 인원 수 조회")
    public void  getParticipantCnt() throws Exception{
        //given
        Long cnt = chatRoomParticipantRepository.findByParticipantCount(2L);
        //when

        //then

        System.out.println("몇명인가?"+ cnt);

    }
    @Test
    @DisplayName("유저가 참여한 채팅방 조회하기")
    public void getChatRoom() throws Exception{
        //given
        ChatRoom chatRoom = chatRoomParticipantRepository.existsParticipantChatRoom(1L, 1L).get();
        //when

        //then
        System.out.println(chatRoom.getChatRoomId());
        System.out.println(chatRoom.getRedisRoomId());
        System.out.println(chatRoom.getRoomName());
    }

}