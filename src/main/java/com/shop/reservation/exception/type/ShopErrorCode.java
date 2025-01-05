package com.shop.reservation.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShopErrorCode implements CommonErrorCode {

    // shop validation check
    NO_REGISTRATION_AUTHORITY(HttpStatus.FORBIDDEN.value(), "매장 등록 권한이 없습니다."),
    ALREADY_REGISTERED_SHOP(HttpStatus.BAD_REQUEST.value(), "이미 등록된 매장입니다."),
    LIMIT_NAME_CHARACTERS_FROM_1_TO_100(HttpStatus.BAD_REQUEST.value(), "매장명 길이는 최소 1자, 최대 100자로 제한됩니다.")
    ;

    private final int statusCode; // http 상태코드
    private final String errorMessage; // 에러메시지
}
