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
    NOT_REGISTERED_SHOP(HttpStatus.BAD_REQUEST.value(), "등록되지 않은 매장입니다."),
    NOT_SHOP_IN_CHARGE(HttpStatus.BAD_REQUEST.value(), "담당 매장이 아닙니다."),
    REMOVED_SHOP(HttpStatus.BAD_REQUEST.value(), "삭제된 매장입니다."),
    FAIL_REMOVE_SHOP(HttpStatus.INTERNAL_SERVER_ERROR.value(), "매장이 삭제되지 않았습니다."),

    INVALID_TEL_NUMBER(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 전화번호입니다."),
    LIMIT_NAME_CHARACTERS_FROM_1_TO_100(HttpStatus.BAD_REQUEST.value(), "매장명 길이는 최소 1자, 최대 100자로 제한됩니다."),
    LIMIT_GROUP_CATEGORY_CHARACTERS_FROM_1_TO_50(HttpStatus.BAD_REQUEST.value(), "그룹 카테고리명 길이는 최소 1자, 최대 50자로 제한됩니다."),
    LIMIT_ADDRESS_CHARACTERS_FROM_1_TO_200(HttpStatus.BAD_REQUEST.value(), "주소 길이는 최소 1자, 최대 200자로 제한됩니다."),
    LIMIT_ROAD_ADDRESS_CHARACTERS_FROM_1_TO_200(HttpStatus.BAD_REQUEST.value(), "도로명주소 길이는 최소 1자, 최대 200자로 제한됩니다."),
    LIMIT_PRECISION_LENGTH_18(HttpStatus.BAD_REQUEST.value(), "위도, 경도 좌표는 총 길이 18자 이하, 소수점 아래 13자 이하로 제한됩니다."),

    // shop list search request param validation check
    REQUEST_PARAM_LATITUDE_IS_NULL(HttpStatus.BAD_REQUEST.value(), "위도를 입력하세요."),
    REQUEST_PARAM_LONGITUDE_IS_NULL(HttpStatus.BAD_REQUEST.value(), "경도를 입력하세요.")
    ;

    private final int statusCode; // http 상태코드
    private final String errorMessage; // 에러메시지
}
