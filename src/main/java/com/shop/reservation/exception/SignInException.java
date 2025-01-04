package com.shop.reservation.exception;

import com.shop.reservation.exception.type.SignInErrorCode;
import lombok.Getter;

@Getter
public class SignInException extends BaseAbstractException {

    private final SignInErrorCode errorCode;
    private final String errorMessage;
    private final int statusCode;

    public SignInException(SignInErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
        this.statusCode = errorCode.getStatusCode();
    }
}
