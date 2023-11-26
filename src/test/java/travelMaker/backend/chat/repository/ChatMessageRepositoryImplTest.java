package travelMaker.backend.chat.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import travelMaker.backend.chat.model.ChatMessage;
import travelMaker.backend.chat.repository.message.ChatMessageRepository;

@SpringBootTest
class ChatMessageRepositoryImplTest {
    @Autowired
    private ChatMessageRepository chatMessageRepository;


    @Test
    @DisplayName("페이징 처리해서 데이터 가져오기")
    public void getTop100Message() throws Exception{
        //given
        Pageable pageable = PageRequest.of(2, 50); // 0~50 시 120~71까지 보임 , 1~50시 70 ~ 21까지 보임, 2~50 20~1까지 보임
        //when
        Page<ChatMessage> results = chatMessageRepository.findTop100ByChatRoomIdOrderByCreatedAt(1L, pageable);

        //then
        for (ChatMessage result : results) {
            System.out.println(result.getChatId());
        }
        System.out.println("총 페이지수 : "+results.getTotalPages());
        System.out.println("총 요소수 : "+results.getTotalElements());
        System.out.println("pageSize :" + results.getSize());
        System.out.println("pageNumber : " + results.getNumber());
        System.out.println("가져온 메시지 수 : " + results.getNumberOfElements());
    }

    @Test
    @DisplayName("가장 최근 메시지 조회하기")
    public void  가장_최근_메시지_조회() throws Exception{
        //given
        ChatMessage latestMessageByChatRoomId = chatMessageRepository.getLatestMessageByChatRoomId(1L).get();
        //when

        //then
        System.out.println("채팅 id : "+latestMessageByChatRoomId.getChatId());
        System.out.println("메시지 : "+latestMessageByChatRoomId.getMessage());
        System.out.println("발신자 : "+latestMessageByChatRoomId.getSender());
        System.out.println("생성시간 : "+latestMessageByChatRoomId.getCreatedAt());
    }
}