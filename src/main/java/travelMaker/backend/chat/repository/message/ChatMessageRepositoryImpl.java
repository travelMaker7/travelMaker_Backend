package travelMaker.backend.chat.repository.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import travelMaker.backend.chat.model.ChatMessage;

import java.util.List;
import java.util.Optional;

import static travelMaker.backend.chat.model.QChatMessage.chatMessage;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ChatMessage> findTop100ByChatRoomIdOrderByCreatedAt(Long chatRoomId,  Pageable pageable) {
        List<ChatMessage> messages = queryFactory.select(chatMessage)
                .from(chatMessage)
                .where(chatMessage.chatRoom.chatRoomId.eq(chatRoomId))
                .orderBy(chatMessage.chatId.desc())
                .offset(pageable.getOffset()) // 몇번 index 부터 시작
                .limit(pageable.getPageSize()) // 한번 조회시 몇개까지 조회
                .fetch();
        int totalSize = queryFactory.selectFrom(chatMessage)
                .where(chatMessage.chatRoom.chatRoomId.eq(chatRoomId))
                .fetch().size();
        return new PageImpl<>(messages, pageable, totalSize);
    }

    @Override
    public Optional<ChatMessage> getLatestMessageByChatRoomId(Long chatRoomId) {
        ChatMessage result = queryFactory.selectFrom(chatMessage)
                .where(
                        chatMessage.chatRoom.chatRoomId.eq(chatRoomId)
                )
                .orderBy(chatMessage.createdAt.desc())
                .limit(1)
                .fetchOne();
        return Optional.ofNullable(result);

    }


}
