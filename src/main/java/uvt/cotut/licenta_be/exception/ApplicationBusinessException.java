package uvt.cotut.licenta_be.exception;

import org.springframework.web.server.ResponseStatusException;

public class ApplicationBusinessException extends ResponseStatusException {
    public ApplicationBusinessException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(),errorCode.getErrorInfo());
    }
}
