package travelMaker.backend.chat.repository.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import travelMaker.backend.chat.model.ChatMessage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatMessageRepositoryTest {
    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Test
    @DisplayName("가장 최근 내용을 가져와야함")
    public void  getLatestMessage() throws Exception{
        //given
        ChatMessage chatMessage = chatMessageRepository.getLatestMessageByChatRoomId(1L).get();
        //when

        //then
        System.out.println(chatMessage.toString());

    }

}