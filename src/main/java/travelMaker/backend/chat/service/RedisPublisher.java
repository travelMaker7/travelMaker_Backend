package travelMaker.backend.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import travelMaker.backend.chat.dto.ChatMessageDto;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, ChatMessageDto> redisTemplate;
    private final ChannelTopic channelTopic;
    public void publish(ChatMessageDto chatMessageDto){
        //redis topic에 메시지 발행, 발행 후 대기중이던 redis 구독서비스가 메시지 처리
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageDto); // pub으로 들어온걸 sub로 바꿔서 쏴줌
    }
}
