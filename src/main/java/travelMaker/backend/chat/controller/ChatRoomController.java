package travelMaker.backend.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.chat.dto.request.OneToOneChatRoomDto;
import travelMaker.backend.chat.dto.response.ChatRoomIdDto;
import travelMaker.backend.chat.dto.response.ChatRoomList;
import travelMaker.backend.chat.service.ChatRoomService;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.user.login.LoginUser;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@Tag(name = "ChatRoom Controller")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;


    // 1:1 채팅방 생성
    @PostMapping("/room")
    @Operation(summary = "1:1 채팅방 생성")
    public ResponseDto<ChatRoomIdDto> createChatRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody OneToOneChatRoomDto oneToOneChatRoomDto
            ){
        log.info("createChatRoom - 1:1 채팅방 생성");
        return ResponseDto.success("채팅방 생성 완료", chatRoomService.createChatRoom(oneToOneChatRoomDto, loginUser.getUser()));
    }
    // 1:n 채팅방 생성
    @Operation(summary = "1:n 채팅방 생성")
    @PostMapping("/room/{tripPlanId}")
    public ResponseDto<ChatRoomIdDto> createGroupChatRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestParam(required = false, defaultValue = "채팅방") String roomName,
            @PathVariable Long tripPlanId
    ){
        log.info("createGroupChatRoom - 그룹 채팅방 생성");
        return ResponseDto.success("그룹 채팅방 생성 완료", chatRoomService.createGroupChatRoom(tripPlanId, roomName, loginUser.getUser()));
    }

    // 채팅방 입장 api
    @Operation(summary = "채팅방 입장")
    @GetMapping("/room/{redisRoomId}") // chatRoomId로 해야하는지 redisRoomId로 해야하는지? 둘다 받자
    public ResponseDto<Void> enterFindChatRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable String redisRoomId,
            @RequestParam Long chatRoomId
            ){

        log.info("enterFindChatRoom - 채팅방 입장");
        log.info("채팅방 입장 ~~~!!!");
        log.info("redis Id : {}", redisRoomId);
        log.info("chatRoomId : {}", chatRoomId);
        log.info("닉네임 : {}", loginUser.getUser().getNickname());
        chatRoomService.enterChatRoom(redisRoomId, chatRoomId, loginUser.getUser());
        return ResponseDto.success("채팅방 입장 완료");
    }

    // 채팅방 목록 조회 api
    @Operation(summary = "유저의 채팅방 목록 조회")
    @GetMapping("/rooms")
    public ResponseDto<ChatRoomList> findAllRooms(@AuthenticationPrincipal LoginUser loginUser){
        log.info("findAllRooms - 채팅방 목록 조회 ");
        return ResponseDto.success("채팅방 목록 조회 성공", chatRoomService.getChatRooms(loginUser.getUser()));
    }



}
