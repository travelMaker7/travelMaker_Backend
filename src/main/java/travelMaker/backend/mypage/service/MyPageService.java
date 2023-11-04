package travelMaker.backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;
import travelMaker.backend.mypage.repository.BookMarkRepository;
import travelMaker.backend.user.login.LoginUser;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final BookMarkRepository bookMarkRepository;
    @Transactional(readOnly = true)
    public BookMarkPlansDto getBookMarkList(LoginUser loginUser) {

        List<BookMarkPlansDto.BookMarkDto> BookMarkScheduleList = bookMarkRepository.bookMark(loginUser.getUser().getUserId());
        return BookMarkPlansDto.builder()
                .schedules(BookMarkScheduleList)
                .build();
    }

}
