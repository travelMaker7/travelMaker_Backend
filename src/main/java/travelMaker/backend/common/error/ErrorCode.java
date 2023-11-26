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
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND", "해당하는 북마크가 존재하지 않습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND", "해당하는 일정이 존재하지 않습니다."),
    NOT_THE_PERSON_WHO_REGISTERED_THE_SCHEDULE(HttpStatus.FORBIDDEN, "일정 삭제 권한 없음", "현재 로그인 한 회원 ID가 해당 일정을 등록한 회원 ID와 일치하지 않습니다."),
    USER_BAD_REQUEST(HttpStatus.BAD_REQUEST,"본인이 본인 프로필 클릭", "잘못된 접근 입니다"),
    MANNER_SCORE_MUST_BE_ZERO_OR_HIGHER(HttpStatus.BAD_REQUEST, "유효하지 않은 매너온도", "매너온도는 0 이상이어야 합니다."),
    TRIP_PLAN_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "동행 보유 여행지","동행자가 있는 여행지일정을 변경할 수 없습니다" ),
    TRIP_PLAN_DELETE_FAIL(HttpStatus.BAD_REQUEST, "동행 보유 여행지","동행자가 있는 여행지일정을 삭제할 수 없습니다" ),
    SCHEDULE_NOT_OWNED_BY_USER(HttpStatus.BAD_REQUEST, "작성자 다름",  "스케줄이 해당 사용자에 의해 작성되지 않았습니다"),
    CHAT_ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "채팅방 없음", "조회할 채팅방이 없습니다"),
    CONNECTION_FAIL(HttpStatus.BAD_REQUEST, "소켓 연결 불가","Command 상태 없음"),
    PARTICIPANT_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 참여한 채팅방 없음", "유저가 참여한 채팅방이 없습니다"),
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅메시지 없음", "유저가 참여한 채팅방에 채팅메시지가 없습니다"),
    WRITE_VALUE_AS_STRING(HttpStatus.INTERNAL_SERVER_ERROR,"I/O 에러" , "메시지 객체를 문자열로 변환하는 과정에서 오류가 발생하였습니다"),
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
