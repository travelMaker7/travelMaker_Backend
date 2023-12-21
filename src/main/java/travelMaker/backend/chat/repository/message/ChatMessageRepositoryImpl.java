package travelMaker.backend.chat.repository.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import travelMaker.backend.chat.model.ChatMessage;

import java.util.List;
import java.util.Optional;

import static travelMaker.backend.chat.model.QChatMessage.chatMessage;
@Slf4j
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageCustom {

    private final JPAQueryFactory queryFactory;

    // 최신순으로 채팅메시지를 가져온다 100개 -> todo pageable로 설정해줄것임
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

    // 채팅방 목록조회시 가장 최근 내용이 보이도록 설정
    @Override
    public Optional<ChatMessage> getLatestMessageByChatRoomId(Long chatRoomId) {
        ChatMessage result = queryFactory.selectFrom(chatMessage)
                .where(
                        chatMessage.chatRoom.chatRoomId.eq(chatRoomId)
                )
                .orderBy(chatMessage.createdAt.desc())
                .fetchFirst();
        return Optional.ofNullable(result);

    }


}
