package com.shop.reservation.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SignUpErrorCode implements CommonErrorCode {

    // 회원ID validation check
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST.value(), "이미 등록된 회원입니다."),

    // 이름 validation check
    LIMIT_NAME_CHARACTERS_FROM_1_TO_50(HttpStatus.BAD_REQUEST.value(), "사용자명 길이는 최소 1자, 최대 50자로 제한됩니다."),

    // 비밀번호 validation check
    LIMIT_PASSWORD_CHARACTERS_FROM_8_TO_100(HttpStatus.BAD_REQUEST.value(), "비밀번호는 최소 8자 이상 최대 100자 이하입니다."),

    // 전화번호 validation check
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 전화번호입니다."),
    ALREADY_REGISTERED_PHONE_NUMBER(HttpStatus.BAD_REQUEST.value(), "이미 등록된 전화번호입니다.")
    ;

    private final int statusCode; // http 상태코드
    private final String errorMessage; // 에러메시지
}
