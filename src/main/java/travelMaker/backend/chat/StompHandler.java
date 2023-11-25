package travelMaker.backend.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import travelMaker.backend.chat.dto.ChatMessageDto;
import travelMaker.backend.chat.service.ChatMessageService;
import travelMaker.backend.chat.service.ChatRoomService;
import travelMaker.backend.chat.service.RedisPublisher;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.jwt.JwtUtils;

import java.time.LocalDateTime;
import java.util.List;

import static travelMaker.backend.jwt.JwtProperties.HEADER_STRING;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtils jwtUtils;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final RedisTemplate<String, ChatMessageDto> redisTemplate;
    private final ChannelTopic channelTopic;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("---------------- 소켓 실행 전 인터셉터를 통해 토큰 검증 --------------------");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        StompCommand command = headerAccessor.getCommand();
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        log.info("아마 토큰 : {}",token);
        log.info("채널 상태 : {}",command);
        if(command == null) throw new GlobalException(ErrorCode.CONNECTION_FAIL);
        String sessionId = headerAccessor.getSessionId();
        log.info("sessionId 상태 : {}",sessionId);
//        String simpSessionId = (String) message.getHeaders().get("simpSessionId");
        messageHandler(command, sessionId, headerAccessor);
        return message;
    }

    private void messageHandler(StompCommand command, String sessionId, StompHeaderAccessor accessor) {
        switch (command){
            case CONNECT:
                // 토큰 검증
                log.info("토큰 검증");
                validationToken(accessor);
                break;
            case SUBSCRIBE:
                // todo 입장 처리 해주기
                log.info("구독");
                clientEnter(accessor, sessionId);
                break;
            case SEND:
                log.info("소켓 통신");
                // todo 소켓통신하기 - 레디스에 저장 -> chatMessageController -> chatMessageService
                break;
            case DISCONNECT:
                // todo 대화 내용 저장
                log.info("연결 해제");

                // todo 누가 나갔는지 알려주기
                break;
        }
    }


    private void clientEnter(StompHeaderAccessor accessor, String sessionId) {
        String destination = accessor.getDestination();
        log.info("destination: {}", destination);
        if(destination == null){
            throw new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        String roomId = chatMessageService.getRoomId(destination);
        log.info("roomId: {}", roomId);

        // 특정 세션이 어느 채팅방에 접속해 있는지 알기 위해 저장
        chatRoomService.saveConnectEnterInfo(sessionId, roomId);

//        String nickname = accessor.getFirstNativeHeader("nickname");
        ChatMessageDto chatMessage = ChatMessageDto.builder()
                .redisRoomId(roomId) // 수정해주기~ 나중ㅇ에!!
//                .nickname(nickname)
                .messageType(ChatMessageDto.MessageType.ENTER)
                .createdAt(LocalDateTime.now())
                .build();

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
        // todo 누가 들어왔는지 알려주기
//        log.info("누가 입장 했나? {}", chatMessage.getNickname());
    }


    private void validationToken(StompHeaderAccessor accessor) {
        String token = jwtUtils.parseJwt(accessor.getFirstNativeHeader(HEADER_STRING));
        jwtUtils.validationJwt(token);
    }


}
