package uvt.cotut.licenta_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode{
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not Found"),
    USER_ALREADY_TAKEN(HttpStatus.BAD_REQUEST, "Email is already registered");

    private final HttpStatus httpStatus;
    private final String errorInfo;
}
