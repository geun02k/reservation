package com.shop.reservation.exception;

import com.shop.reservation.exception.type.ShopErrorCode;
import lombok.Getter;

@Getter
public class ShopException extends BaseAbstractException {

    private final ShopErrorCode errorCode;
    private final String errorMessage;
    private final int statusCode;

    public ShopException(ShopErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
        this.statusCode = errorCode.getStatusCode();
    }
}
