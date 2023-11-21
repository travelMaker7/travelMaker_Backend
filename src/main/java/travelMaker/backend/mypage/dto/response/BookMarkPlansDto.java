package travelMaker.backend.mypage.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class BookMarkPlansDto {
    private List<BookMarkDto> schedules;
    @Getter
    @AllArgsConstructor
    @ToString
    public static class BookMarkDto {
        private Long scheduleId;
        private String scheduleName;
        private String scheduleDescription;
        private String nickname;
//        private String region;
    }
}
