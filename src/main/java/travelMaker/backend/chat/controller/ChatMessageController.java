package travelMaker.backend.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import travelMaker.backend.chat.dto.ChatMessageDto;
import travelMaker.backend.chat.dto.response.ChatMessageList;
import travelMaker.backend.chat.service.ChatMessageService;
import travelMaker.backend.chat.service.ChatRoomService;
import travelMaker.backend.common.dto.ResponseDto;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;


    // pub - 대화 및 저장
//    @MessageMapping("/chat/message")
//    public void message(ChatMessageDto messageDto){
//        chatRoomService.enterMessageRoom(messageDto.getRedisRoomId());
//        chatMessageService.chatMessageSave(messageDto);
//    }

    // 대화 내역 조회 api
     @Operation(summary = "대화 내역 조회")
    @GetMapping("/api/v1/chat/room/{redisRoomId}/messages")
    public ResponseDto<ChatMessageList> loadMessage(
            @PathVariable String redisRoomId,
            @RequestParam Long chatRoomId,
            Pageable pageable
    ){
        //pageable를 받으면 QueryString으로 오는 page와 size를 알아서 주입해줌 ?page=3&size=4
        //pageNumber=3 page=3 페이지 번호 (0부터 시작) -> totalElements / pageSize : 120/50 -> 2,  0부터 시작 => 0, 1, 2가 있음
        //pageSize 몇개까지  =4 size=4 한 페이지에서 나타내는 원소의 수(게시글수) -> 50으로 설정
        // offset 몇번째부터  = 해당 페이지에 첫번째 원소의 수 -> 0 ~ 49, 50~99, 100~149 -> 0, 50, 100 (시작원소 인덱스)
        // totalPages : 페이지로 제공되는 총 페이지수 -> 요청시 50개 단위로 끊었을 때 -> totalElements / 50(pagesize) 값
        // totalElements : 모든 페이지에 존재하는 총 원소의 수 -> 채팅 전체

        // 채팅이 120개가 있다 50개씩 불러오고자 할 떄
        // totalElements = 120
        // pageSize = 50
        // totalPages = 120 / 50 -> 3
        // offset = 0, 50, 100 -> db에서 사용
        // pageNumber = totalPages / pageNumber -> totalPages / (totalPages / offset)

        return ResponseDto.success("대화 내용 조회 성공", chatMessageService.loadMessage(redisRoomId, chatRoomId, pageable));
    }
}
