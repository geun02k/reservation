package com.shop.reservation.exception;

import com.shop.reservation.exception.type.SignUpErrorCode;
import lombok.Getter;

@Getter
public class SignUpException extends BaseAbstractException {

    private final SignUpErrorCode errorCode;
    private final String errorMessage;
    private final int statusCode;

    public SignUpException(SignUpErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
        this.statusCode = errorCode.getStatusCode();
    }

}
