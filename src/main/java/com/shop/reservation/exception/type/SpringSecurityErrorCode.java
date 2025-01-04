package com.shop.reservation.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SpringSecurityErrorCode implements CommonErrorCode {

    // login validation check
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 사용자입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다.")
    ;

    private final int statusCode; // http 상태코드
    private final String errorMessage; // 에러메시지
}
