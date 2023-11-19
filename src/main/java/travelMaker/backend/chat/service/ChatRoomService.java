package travelMaker.backend.chat.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.chat.dto.ChatRoomDto;
import travelMaker.backend.chat.dto.response.ChatRoomList;
import travelMaker.backend.chat.dto.response.ChatRoomPreviewDto;
import travelMaker.backend.chat.model.ChatMessage;
import travelMaker.backend.chat.model.ChatRoom;
import travelMaker.backend.chat.model.ChatRoomParticipant;
import travelMaker.backend.chat.repository.message.ChatMessageRepository;
import travelMaker.backend.chat.repository.participant.ChatRoomParticipantRepository;
import travelMaker.backend.chat.repository.ChatRoomRepository;
import travelMaker.backend.common.error.ErrorCode;
import travelMaker.backend.common.error.GlobalException;
import travelMaker.backend.tripPlan.model.TripPlan;
import travelMaker.backend.tripPlan.repository.TripPlanRepository;
import travelMaker.backend.user.model.User;
import travelMaker.backend.user.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final TripPlanRepository tripPlanRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private Map<String, ChannelTopic> topics = new HashMap<>();
    private static final String CHAT_ROOMS = "CHAT_ROOMS";
    private static final String ENTER_INFO = "ENTER_INFO";


    @Resource(name = "redisRoomTemplate")
    private HashOperations<String, String, ChatRoomDto> roomHashOperations;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> enterHashOperations;

    /**
     * 1:1 채팅방 생성 : 서버간 채팅방 공유를 위해 Redis hash에 저장
     */
    @Transactional
    public String createChatRoom(String roomName, User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        // 채팅방 생성 및 저장
        ChatRoomDto chatRoomDto = ChatRoomDto.createChatRoom();
        log.info("레디스 id {}", chatRoomDto.getRedisRoomId()); //redisId
        ChatRoom chatRoom = ChatRoom.builder()
                .redisRoomId(chatRoomDto.getRedisRoomId())
                .roomName(roomName)
                .build();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // 유저-채팅방 중간테이블 생성 및 저장
        ChatRoomParticipant chatRoomParticipant = ChatRoomParticipant.builder()
                .chatRoom(savedChatRoom)
                .user(user).build();

        chatRoomParticipantRepository.save(chatRoomParticipant);

        roomHashOperations.put(CHAT_ROOMS, chatRoomDto.getRedisRoomId(), chatRoomDto); // 레디스에 저장
        return chatRoomDto.getRedisRoomId(); // 채팅방 id
    }
    /**
     * 그룹 채팅방 생성 : 서버간 채팅방 공유를 위해 Redis hash에 저장
     */

    @Transactional
    public String createGroupChatRoom(Long tripPlanId, String roomName, User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(() -> new GlobalException(ErrorCode.TRIP_PLAN_NOT_FOUND));
        // 채팅방 생성 및 저장
        ChatRoomDto chatRoomDto = ChatRoomDto.createChatRoom();
        log.info("레디스 id {}", chatRoomDto.getRedisRoomId()); //redisId
        ChatRoom chatRoom = ChatRoom.builder()
                .redisRoomId(chatRoomDto.getRedisRoomId())
                .roomName(roomName)
                .tripPlan(tripPlan)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // 유저-채팅방 중간테이블 생성 및 저장
        ChatRoomParticipant chatRoomParticipant = ChatRoomParticipant.builder()
                .chatRoom(savedChatRoom)
                .user(user).build();

        chatRoomParticipantRepository.save(chatRoomParticipant);

        roomHashOperations.put(CHAT_ROOMS, chatRoomDto.getRedisRoomId(), chatRoomDto); // 레디스에 저장
        return chatRoomDto.getRedisRoomId(); // 채팅방 id
    }


    /**
     * 접속한 사용자의 세션 id를 입장한 채팅방 id와 매핑 정보 저장
     */
//    public void saveConnectEnterInfo(String sessionId, String roomId){
//        enterHashOperations.put(ENTER_INFO, sessionId, roomId);
//    }
    /**
     * 유저 세션으로 입장해 있는 채팅방 id 조회
     */
//    public String getRoomIdBySessionId(String userSessionId){
//        return enterHashOperations.get(ENTER_INFO, userSessionId);
//    }
    /**
     * 유저 세션정보와 매핑된 채팅방 id 삭제
     */
//    public void removeUserInfo(String userSessionId){
//        enterHashOperations.delete(ENTER_INFO, userSessionId);
//    }


//    public void enterMessageRoom(String redisRoomId) {
//        ChannelTopic topic = topics.get(redisRoomId);
//        if(topic == null){
//            topic = new ChannelTopic(redisRoomId);
//            topics.put(redisRoomId, topic);
//        }
//    }


    /**
     * 채팅방 목록 조회 : 사용자가 참여한 대화방의 목록을 조회한다
     */

    public ChatRoomList getChatRooms(User user){
        List<ChatRoomPreviewDto> chatRoomPreviewDtos = new ArrayList<>();

        userRepository.findById(user.getUserId()).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        // 유저가 참여한 채팅방들을 조회한다
        List<ChatRoomParticipant> participantChatRoomList = chatRoomParticipantRepository.findByUserId(user.getUserId());
        log.info("유저가 참여한 채팅방 수 : {}", participantChatRoomList.size());
        for (ChatRoomParticipant chatRoomParticipant : participantChatRoomList) {
            Long chatRoomId = chatRoomParticipant.getChatRoom().getChatRoomId();
            log.info("채팅방 id : {}", chatRoomId);
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
            // 해당 채팅방 참여 인원 수를 조회 한다
            Long participantCount = chatRoomParticipantRepository.findByParticipantCount(chatRoomId);

            log.info("참여자 수 : {}", participantCount);
            // 해당 채팅방의 최근 대화내용과 시간을 조회한다
            ChatMessage latestMessage = chatMessageRepository.getLatestMessageByChatRoomId(chatRoomId).orElseThrow(() -> new GlobalException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));
            ChatRoomPreviewDto chatRoomPreviewDto = ChatRoomPreviewDto.builder()
                    .roomName(chatRoom.getRoomName())
                    .recentTalk(latestMessage.getMessage() == null ? "" : latestMessage.getMessage())
                    .recentTalkDate(latestMessage.getCreatedAt())
                    .partnerCount(participantCount)
                    .build();
            chatRoomPreviewDtos.add(chatRoomPreviewDto);
        }


        return ChatRoomList.builder()
                .chatRooms(chatRoomPreviewDtos)
                .build();
    }
    /**
     * 선택한 채팅방 조회 :
     * 1. 채팅내역이 보인다
     * 2. 수신자와 발신자가 보인다
     *
     */
//    public void getSelectedChatRoom(String roomId, User user) {
//        userRepository.findById(user.getUserId()).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
//        ChatRoom chatRoom = chatRoomRepository.findByRedisRoomId(roomId).orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
//        chatMessageRepository.findByChatRoomId(chatRoom.getChatRoomId());
//
//
//    }

    /**
     * 채팅방 입장 : 레디스에 특정 roomId에 닉네임저장
     */

    // redis 채널에서 쪽지방 조회
//    public ChannelTopic getTopic(String redisRoomId){
//        return topics.get(redisRoomId);
//    }
}
