package com.shop.reservation.exception;

import com.shop.reservation.exception.type.SignUpErrorCode;

public class SignUpException extends BaseAbstractException {

    private final SignUpErrorCode errorCode;
    private final String errorMessage;

    public SignUpException(SignUpErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
    }

    @Override
    public SignUpErrorCode getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public int getStatusCode() {
        return errorCode.getStatusCode();
    }

}
