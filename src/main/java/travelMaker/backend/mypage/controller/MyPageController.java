package travelMaker.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import travelMaker.backend.common.dto.ResponseDto;
import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;
import travelMaker.backend.mypage.service.MyPageService;
import travelMaker.backend.user.login.LoginUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "mypage controller")
@RequestMapping("/api/v1")
public class MyPageController {
    private final MyPageService myPageService;
    @GetMapping("/mypage/bookmark")
    @Operation(summary = "북마크 목록 조회")
    public ResponseDto<BookMarkPlansDto> getBookMarkList(@AuthenticationPrincipal LoginUser loginUser){
        return ResponseDto.success("북마크 목록 조회 성공", myPageService.getBookMarkList(loginUser));
    }


}
