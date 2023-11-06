package travelMaker.backend.mypage.repsoitory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import travelMaker.backend.mypage.dto.response.BookMarkPlansDto;
import travelMaker.backend.mypage.repository.BookMarkRepository;

@SpringBootTest
@Rollback(value=false)
public class BookMarkServiceTest {

    @Autowired
    BookMarkRepository bookMarkRepository;


    @Test
    @DisplayName("북마크한 여행 목록 가져오기")
    public void bookMark() throws Exception{

        Long userId = 5L;
        for (BookMarkPlansDto.BookMarkDto bookMarkDto : bookMarkRepository.bookMark(userId)){
            System.out.println(bookMarkDto);
        }


    }
}
