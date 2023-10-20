package travelMaker.backend.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
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
