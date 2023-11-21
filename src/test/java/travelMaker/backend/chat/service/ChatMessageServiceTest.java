package travelMaker.backend.chat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import travelMaker.backend.chat.dto.ChatMessageDto;

@SpringBootTest
class ChatMessageServiceTest {
    @Autowired
    ChatMessageService chatMessageService;


    @Test
    @DisplayName("채팅 내역 가져오기")
    public void  getAllMessage() throws Exception{
        //given
        Pageable pageRequest = PageRequest.of(2, 3); // 1 2 3 4 5 -> 2개씩 짤라서 3, 4 보여야 함
        //when
        chatMessageService.loadMessage("hello",1L, pageRequest);
        //then

    }
    @Test
    @DisplayName("채팅 저장하기")
    public void  saveMessage() throws Exception{
        //given
        ChatMessageDto messageDto = ChatMessageDto.builder()
                .redisRoomId("hello")
                .nickname("tbrktbrk")
                .senderId(1L)
                .chatRoomId(1L)
                .message("안녕 테스트~ redis 2번쨰~").build();
        //when
        chatMessageService.chatMessageSave(messageDto);
        //then

    }

}