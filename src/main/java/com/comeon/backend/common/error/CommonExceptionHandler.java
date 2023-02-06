package com.comeon.backend.common.error;

import com.comeon.backend.common.response.ErrorResponse;
import com.comeon.backend.common.response.ResponseEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@Order(Integer.MAX_VALUE)
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> allErrorHandle(Exception e) {
        log.error("{}", e.getClass().getSimpleName(), e);
        return ResponseEntityUtils.buildResponseByErrorCode(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandle(RestApiException e) {
        log.error("{}", e.getClass().getSimpleName(), e);
        return ResponseEntityUtils.buildResponseByErrorCode(e.getErrorCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> invalidJsonHandle(HttpMessageNotReadableException e) {
        ErrorCode errorCode = CommonErrorCode.INVALID_JSON;
        return ResponseEntityUtils.buildResponseByErrorCode(errorCode);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> notSupportedHttpMethodHandle(HttpRequestMethodNotSupportedException e) {
        ErrorCode errorCode = CommonErrorCode.HTTP_METHOD_NOT_SUPPORTED;
        return ResponseEntityUtils.buildResponseByErrorCode(errorCode);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingPathVariableException.class})
    public ResponseEntity<ErrorResponse> typeErrorHandle(Exception e) {
        ErrorCode errorCode = CommonErrorCode.BIND_ERROR;
        return ResponseEntityUtils.buildResponseByErrorCode(errorCode);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResponse> typeMismatchHandle(TypeMismatchException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntityUtils.buildResponseByErrorCode(errorCode);
    }
}
