package com.shop.reservation.exception.handler;

import com.shop.reservation.exception.BaseAbstractException;
import com.shop.reservation.exception.model.CommonErrorResponse;
import com.shop.reservation.exception.type.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

import static com.shop.reservation.exception.type.DefaultErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
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

    @ExceptionHandler
    protected ResponseEntity<CommonErrorResponse> defaultHandler(Exception e) {
        log.warn(e.getMessage(), e);

        // 서버에러객체생성
        DefaultErrorCode errorCode = INTERNAL_SERVER_ERROR;

        // 응답 객체 생성
        CommonErrorResponse errorResponse = CommonErrorResponse.builder()
                .statusCode(errorCode.getStatusCode())
                .errorCode(errorCode)
                .errorMessage(errorCode.getErrorMessage())
                .build();

        // 응답 객체 반환
        // HttpStatus.resolve(e.getStatusCode()) 를 통해 HttpStatus에 상태코드를 담아서
        // errorResponse와 함께 Http 응답으로 내려보낸다.
        return new ResponseEntity<>(errorResponse,
                Objects.requireNonNull(HttpStatus.resolve(errorCode.getStatusCode())));
    }

}
