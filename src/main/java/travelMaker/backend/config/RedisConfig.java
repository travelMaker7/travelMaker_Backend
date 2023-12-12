package travelMaker.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import travelMaker.backend.chat.dto.ChatMessageDto;
import travelMaker.backend.chat.dto.ChatRoomEnterInfo;
import travelMaker.backend.chat.service.RedisSubscriber;
import travelMaker.backend.chat.dto.ChatRoomDto;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }
    /**
     * 어플리케이션에서 사용할 redisTemplate 설정
     */
    @Bean
    public RedisTemplate<String, ChatMessageDto> redisMessageTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatMessageDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // chatMessageDto객체를 JSON으로 직렬화
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDto.class));

        // Hash Operation 사용 시
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    @Bean
    public RedisTemplate<String, ChatRoomDto> redisRoomTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatRoomDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // chatMessageDto객체를 JSON으로 직렬화
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatRoomDto.class));

        // Hash Operation 사용 시
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ChatRoomDto.class));
        return redisTemplate;
    }
    @Bean
    public RedisTemplate<String, ChatRoomEnterInfo> redisEnterInfoTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatRoomEnterInfo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // chatMessageDto객체를 JSON으로 직렬화
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatRoomEnterInfo.class));

        // Hash Operation 사용 시
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ChatRoomEnterInfo.class));
        return redisTemplate;
    }

    /**
     * 단일 Topic 사용을 위한 Bean 설정
     */
    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }
    /**
     * redis에 발행(publish)된 메시지 처리를 위한 리스너 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
                                                              MessageListenerAdapter listenerAdapter,
                                                              ChannelTopic channelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }

    /**
     * 실제 메시지를 처리하는 subscriber 설정 추가
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "sendMessage");
    }
}
