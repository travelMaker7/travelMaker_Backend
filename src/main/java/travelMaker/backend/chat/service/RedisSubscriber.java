package travelMaker.backend.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import travelMaker.backend.chat.dto.ChatMessageDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messageTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
     */
    public void sendMessage(String publishMessage){

        try{
            log.info("받은 메시지 내용 {}", publishMessage);
            // 받은 문자열(JSON 데이터)을 ChatMessageDto 객체로 매핑
            ChatMessageDto chatMessage = objectMapper.readValue(publishMessage, ChatMessageDto.class);
            // websocket 구독자에게 채팅 메시지 전송
            messageTemplate.convertAndSend("/sub/chat/room"+chatMessage.getRedisRoomId(), chatMessage); // 구독자들에게 뿌
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }

}
