package travelMaker.backend.chat.repository.participant;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SortComparator;
import travelMaker.backend.JoinRequest.model.JoinStatus;
import travelMaker.backend.chat.dto.response.ChatRoomPreviewDto;
import travelMaker.backend.chat.model.ChatMessage;
import travelMaker.backend.chat.model.QChatMessage;
import travelMaker.backend.chat.model.QChatRoom;
import travelMaker.backend.chat.model.QChatRoomParticipant;
import travelMaker.backend.user.model.QUser;
import travelMaker.backend.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static travelMaker.backend.JoinRequest.model.QJoinRequest.joinRequest;
import static travelMaker.backend.chat.model.QChatMessage.chatMessage;
import static travelMaker.backend.chat.model.QChatRoom.chatRoom;
import static travelMaker.backend.chat.model.QChatRoomParticipant.chatRoomParticipant;
import static travelMaker.backend.tripPlan.model.QTripPlan.tripPlan;
import static travelMaker.backend.user.model.QUser.user;

@Slf4j
@RequiredArgsConstructor
public class ChatRoomParticipantRepositoryImpl implements ChatRoomParticipantCustom {

    private final JPAQueryFactory queryFactory;




}
