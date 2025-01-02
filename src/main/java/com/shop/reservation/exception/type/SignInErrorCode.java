package com.shop.reservation.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SignInErrorCode implements CommonErrorCode {

    // login validation check
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 사용자입니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다.")
    ;

    private final int statusCode; // http 상태코드
    private final String errorMessage; // 에러메시지
}
