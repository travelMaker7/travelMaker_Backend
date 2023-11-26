package travelMaker.backend.chat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.chat.dto.response.ChatRoomList;
import travelMaker.backend.user.model.User;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChatRoomServiceTest {
    @Autowired
    ChatRoomService chatRoomService;

    @Test
    @DisplayName("사용자가 포함된 채팅방 목록 조회하기")
    public void  getChatRoomList() throws Exception{
        //given
        ChatRoomList chatRooms = chatRoomService.getChatRooms(User.builder().userId(1L).build());
        //when
        System.out.println(chatRooms.getChatRooms().stream().toList());
        //then

    }

}