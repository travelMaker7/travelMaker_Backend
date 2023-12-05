package travelMaker.backend.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.chat.dto.ChatMessageDto;
import travelMaker.backend.chat.dto.response.ChatMessageList;
import travelMaker.backend.chat.model.ChatMessage;
import travelMaker.backend.chat.model.ChatRoom;
import travelMaker.backend.chat.repository.message.ChatMessageRepository;
import travelMaker.backend.chat.repository.ChatRoomRepository;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private static final String CHAT_MESSAGE = "CHAT_MESSAGE";
    @Resource(name = "redisMessageTemplate")
    private RedisTemplate<String, ChatMessageDto> messageListOperations;
    // 메시지 브로커
    private final RedisTemplate<String, ChatMessageDto> redisTemplate;

    private final ChannelTopic channelTopic;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    //todo 채팅 내용 저장
    @Transactional
    public void chatMessageSave(ChatMessageDto chatMessageDto){
        User user = userRepository.findById(chatMessageDto.getSenderId()).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId()).orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        chatMessageDto.setCreatedAt(LocalDateTime.now());
        //DB 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(user)
                .message(chatMessageDto.getMessage())
                .chatRoom(chatRoom)
                .createdAt(chatMessageDto.getCreatedAt())
                .build();
        ChatMessage save = chatMessageRepository.save(chatMessage);
        log.info("채팅 내용 저장 됐나? {}", save);

        // 레디스에 key: redisRoomId, value: chatMessageDto로 저장
        messageListOperations.opsForList().rightPush(chatMessageDto.getRedisRoomId(), chatMessageDto);
        // 만료시간 7일 설정
        messageListOperations.expireAt(chatMessageDto.getRedisRoomId(), Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant()));
        log.info("message : {}",chatMessageDto);
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageDto); // pub으로 들어온걸 sub로 바꿔서 쏴줌
    }


    // destination에서 roomId가져오기
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 채팅 내역을 조회 -> redis먼저 조회 후 db조회
     */
    @Transactional(readOnly = true)
    public ChatMessageList loadMessage(String redisRoomId, Long chatRoomId, Pageable pageable) {
        List<ChatMessageDto> chatMessageList = new ArrayList<>();
        log.info("대화내역 불러오기");
        // redis 에서 해당 채팅방의 메시지 100개 가져오기
        List<ChatMessageDto> redisMessageList = messageListOperations.opsForList().range(redisRoomId, 0, 99);
        // redis에서 해당 채팅방의 메시지가 없다면 , DB 에서 메시지 100개 가져오기
        if(redisMessageList == null || redisMessageList.isEmpty()){
            log.info("redis에 저장된게 없다!");
            Page<ChatMessage> messageList = chatMessageRepository.findTop100ByChatRoomIdOrderByCreatedAt(chatRoomId, pageable);
            for (ChatMessage chatMessage : messageList) {
                chatMessageList.add(ChatMessageDto.from(chatMessage, redisRoomId, chatRoomId));
            }

        }else{
            log.info("redis에 저장된게 있다!");
            chatMessageList.addAll(redisMessageList);
        }

        return ChatMessageList
                .builder()
                .messages(chatMessageList)
//                .currentPage(1) // todo 임시로 작성해둠
//                .totalPages(chatMessageList.size()/pageable.getPageSize())
                .build();
    }
}
