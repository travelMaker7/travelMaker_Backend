package travelMaker.backend.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SCHEDULE_DATE_OVERFLOW(HttpStatus.BAD_REQUEST,"일정일 오류", "일정 종료일이 일정 시작일보다 빠를 수 없습니다"),
    SCHEDULE_TIME_OVERFLOW(HttpStatus.BAD_REQUEST,"쳬류 시간 오류", "도착 시간보다 떠나는 시간이 빠를 수 없습니다")
    ;

    private HttpStatus httpStatus;
    private String status;
    private String message;

    ErrorCode(HttpStatus httpStatus, String status, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }
}
