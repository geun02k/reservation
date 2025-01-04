package com.shop.reservation.exception.handler;

import com.shop.reservation.exception.model.CommonErrorResponse;
import com.shop.reservation.exception.type.CommonErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.shop.reservation.exception.type.SpringSecurityErrorCode.ACCESS_DENIED;
import static com.shop.reservation.exception.type.SpringSecurityErrorCode.INVALID_TOKEN;

@RestControllerAdvice
public class SpringSecurityExceptionHandler {

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
}
