package com.shop.reservation.exception.model;

import com.shop.reservation.exception.type.CommonErrorCode;
import lombok.*;

// 에러 발생 시 응답을 위한 모델.
// 모든 에러에 대해 일괄정 응답을 위해 사용.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonErrorResponse {

    private int statusCode;
    private CommonErrorCode errorCode;
    private String errorMessage;

}
