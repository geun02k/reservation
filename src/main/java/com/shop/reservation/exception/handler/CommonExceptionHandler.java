package com.shop.reservation.exception.handler;

import com.shop.reservation.exception.BaseAbstractException;
import com.shop.reservation.exception.model.CommonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class CommonExceptionHandler {

    /** 예외처리
     *  : 모든 BaseAbstractException을 상속하는 에외객체에 대한 예외처리 수행. */
    @ExceptionHandler
    protected ResponseEntity<CommonErrorResponse> commonHandler(BaseAbstractException e) {
        // 응답 객체 생성
        CommonErrorResponse errorResponse = CommonErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorMessage())
                .build();

        // 응답 객체 반환
        // HttpStatus.resolve(e.getStatusCode()) 를 통해 HttpStatus에 상태코드를 담아서
        // errorResponse와 함께 Http 응답으로 내려보낸다.
        return new ResponseEntity<>(errorResponse,
                Objects.requireNonNull(HttpStatus.resolve(e.getStatusCode())));
    }
}
