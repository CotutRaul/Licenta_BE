package uvt.cotut.licenta_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode{
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not Found"),
    USER_ALREADY_TAKEN(HttpStatus.BAD_REQUEST, "Email is already registered"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"Product not Found"),
    ORDER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Order couldn't be completed"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not Found"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access Denied for this action"),
    SHOP_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "Shop not Found");

    private final HttpStatus httpStatus;
    private final String errorInfo;
}
