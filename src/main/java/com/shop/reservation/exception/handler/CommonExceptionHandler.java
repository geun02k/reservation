package com.shop.reservation.exception.handler;

import com.shop.reservation.exception.BaseAbstractException;
import com.shop.reservation.exception.model.CommonErrorResponse;
import com.shop.reservation.exception.type.CommonErrorCode;
import com.shop.reservation.exception.type.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

import static com.shop.reservation.exception.type.DefaultErrorCode.INTERNAL_SERVER_ERROR;
import static com.shop.reservation.exception.type.SpringSecurityErrorCode.ACCESS_DENIED;
import static com.shop.reservation.exception.type.SpringSecurityErrorCode.INVALID_TOKEN;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonErrorResponse> handleAuthenticationException(
            AuthenticationException e) {
        // 에러코드 지정
        CommonErrorCode errorCode = INVALID_TOKEN;

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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonErrorResponse> handleAccessDeniedException(
            AccessDeniedException e) {
        // 에러코드 지정
        CommonErrorCode errorCode = ACCESS_DENIED;

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
