package travelMaker.backend.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SCHEDULE_DATE_OVERFLOW(HttpStatus.BAD_REQUEST,"일정일 오류", "일정 종료일이 일정 시작일보다 빠를 수 없습니다"),
    SCHEDULE_TIME_OVERFLOW(HttpStatus.BAD_REQUEST,"체류 시간 오류", "도착 시간보다 떠나는 시간이 빠를 수 없습니다"),
    DUPLICATE_JOIN_REQUEST(HttpStatus.CONFLICT, "동행 신청 중복 오류", "이미 동행 신청을 했습니다."),
    TRIP_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND" , "해당하는 일정 여행지가 존재하지 않습니다."),
    JOIN_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND", "해당하는 동행 신청이 존재하지 않습니다."),
    JOIN_CNT_EXCEEDS_WISH_CNT(HttpStatus.BAD_REQUEST, "동행 희망 인원 초과 오류", "동행 확정 인원 수가 동행 희망 인원 수를 초과할 수 없습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_JWT_TOKEN","JWT 토큰이 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Token Expired","JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED,"UNSUPPORTED_JWT_TOKEN", "지원하지 않는 토큰입니다."),
    USERNAME_NOT_FOUND(HttpStatus.UNAUTHORIZED,"계정이 없다","계정이 존재하지 않습니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호 불일치","비밀번호가 불일치 합니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,"리프레시토큰 만료", "리프레시 토큰시간이 만료되었습니다. 다시 로그인 해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"ENTITY_NOT_FOUND", "해당하는 회원이 존재하지 않습니다."),
    USER_BAD_REQUEST(HttpStatus.BAD_REQUEST,"본인이 본인 프로필 클릭", "잘못된 접근 입니다")

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
