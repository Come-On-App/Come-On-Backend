package com.comeon.backend.common.api;

import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandle(RestApiException e) {
        log.error("{}", e.getClass().getSimpleName(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorCode(e.getErrorCode().getCode())
                                .errorDescription(e.getErrorCode().getDescription())
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> invalidJsonHandle(HttpMessageNotReadableException e) {
        CommonErrorCode errorCode = CommonErrorCode.INVALID_JSON;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorCode(errorCode.getCode())
                                .errorDescription(errorCode.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> notSupportedHttpMethodHandle(HttpRequestMethodNotSupportedException e) {
        CommonErrorCode errorCode = CommonErrorCode.HTTP_METHOD_NOT_SUPPORTED;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorCode(errorCode.getCode())
                                .errorDescription(errorCode.getDescription())
                                .build()
                );
    }
}
