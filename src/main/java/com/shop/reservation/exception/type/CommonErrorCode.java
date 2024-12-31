package com.shop.reservation.exception.type;

// @AllArgsConstructor 사용
// -> description 들어가는 생성자를 자동생성.
// -> 코드에 description 작성가능.

public interface CommonErrorCode {
    int getStatusCode();
    String getErrorMessage();
}
