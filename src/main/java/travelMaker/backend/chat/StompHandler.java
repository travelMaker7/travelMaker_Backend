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
import travelMaker.backend.chat.dto.ChatRoomEnterInfo;
import travelMaker.backend.chat.service.ChatMessageService;
import travelMaker.backend.chat.service.ChatRoomService;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.jwt.JwtUtils;
import travelMaker.backend.user.login.LoginUser;

import java.time.LocalDateTime;

import static travelMaker.backend.jwt.JwtProperties.HEADER_STRING;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtils jwtUtils;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    // 메시지 브로커
    private final RedisTemplate<String, ChatMessageDto> redisTemplate;
    private final ChannelTopic channelTopic;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("---------------- StompHandler start --------------------");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        StompCommand command = headerAccessor.getCommand();
        if(command == null) throw new GlobalException(ErrorCode.CONNECTION_FAIL);
        String sessionId = headerAccessor.getSessionId();
        log.info("채널 상태 : {}",command);
        log.info("sessionId 상태 : {}",sessionId);
        messageHandler(command, sessionId, headerAccessor);
        log.info("---------------- StompHandler end --------------------");
        return message;
    }

    private void messageHandler(StompCommand command, String sessionId, StompHeaderAccessor accessor) {
        switch (command){
            case CONNECT:
                // 토큰 검증
                validationToken(accessor);
                break;
            case SUBSCRIBE:
                clientEnter(accessor, sessionId);
                break;
            case SEND:
//                log.info("소켓 통신");
                break;
            case DISCONNECT:
                // todo 대화 내용 저장
//                log.info("연결 해제");
                clientOut(sessionId);
                break;
        }
    }

    private void clientOut(String sessionId) {
        // todo 채팅방 인원수 : -1하기 위함
        ChatRoomEnterInfo enterUserInfo = chatRoomService.getRoomIdBySessionId(sessionId);

        chatRoomService.removeUserInfo(sessionId);
        ChatMessageDto chatMessage = ChatMessageDto.builder()
                .redisRoomId(enterUserInfo.getRedisRoomId())
                .nickname(enterUserInfo.getNickname())
                .message(enterUserInfo.getNickname()+"님이 퇴장하셨습니다!")
                .messageType(ChatMessageDto.MessageType.QUIT)
                .createdAt(LocalDateTime.now())
                .build();

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }


    private void clientEnter(StompHeaderAccessor accessor, String sessionId) {
        String destination = accessor.getDestination();
        String token = accessor.getFirstNativeHeader(HEADER_STRING);
        String jwt = jwtUtils.parseJwt(token);
        LoginUser loginUser = jwtUtils.verify(jwt);
        log.info("구독 - nickname : {}", loginUser.getUser().getNickname());
        if(destination == null){
            throw new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        String redisRoomId = chatMessageService.getRoomId(destination);
        // 특정 세션이 어느 채팅방에 접속해 있는지 알기 위해 저장
        ChatRoomEnterInfo enterUserInfo = ChatRoomEnterInfo.builder()
                .redisRoomId(redisRoomId)
                .nickname(loginUser.getUser().getNickname())
                .build();
        chatRoomService.saveConnectEnterInfo(sessionId, enterUserInfo);
        ChatMessageDto chatMessage = ChatMessageDto.builder()
                .redisRoomId(redisRoomId)
                .nickname(loginUser.getUser().getNickname())
                .senderId(loginUser.getUser().getUserId())
                .message(loginUser.getUser().getNickname()+"님이 입장하셨습니다!")
                .messageType(ChatMessageDto.MessageType.ENTER)
                .createdAt(LocalDateTime.now())
                .build();

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }


    private void validationToken(StompHeaderAccessor accessor) {
        String token = jwtUtils.parseJwt(accessor.getFirstNativeHeader(HEADER_STRING));
        jwtUtils.validationJwt(token);
    }


}
