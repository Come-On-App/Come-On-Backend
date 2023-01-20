package com.comeon.backend.common.api;

import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ValidExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validExceptionHandle(MethodArgumentNotValidException e) {
        List<ErrorResponse.ValidError> errors = parseValidErrors(e);

        ErrorCode errorCode = CommonErrorCode.REQUEST_VALIDATION_FAIL;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorCode(errorCode.getCode())
                                .errorDescription(errorCode.getDescription())
                                .errors(errors)
                                .build()
                );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExceptionHandle(BindException e) {
        List<ErrorResponse.ValidError> errors = parseValidErrors(e);

        CommonErrorCode errorCode = CommonErrorCode.BIND_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorCode(errorCode.getCode())
                                .errorDescription(errorCode.getDescription())
                                .errors(errors)
                                .build()
                );
    }

    private List<ErrorResponse.ValidError> parseValidErrors(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ErrorResponse.ValidError> errors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.ValidError(fieldError, getMessage(fieldError)))
                .collect(Collectors.toList());
        errors.addAll(
                bindingResult.getGlobalErrors().stream()
                        .map(objectError -> new ErrorResponse.ValidError(getMessage(objectError)))
                        .collect(Collectors.toList())
        );

        Object target = e.getTarget();
        if (target != null) {
            log.error(
                    "Valid Fail. TargetClass: {}, Error Fields: {}",
                    target.getClass().getSimpleName(),
                    errors.stream().map(ErrorResponse.ValidError::getField).collect(Collectors.toList())
            );
        }

        return errors;
    }

    private String getMessage(MessageSourceResolvable resolvable) {
        try {
            return messageSource.getMessage(resolvable, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            ObjectError error = (ObjectError) resolvable;
            log.warn("[{}] 코드와 매칭되는 메시지가 없습니다. Code : {}", e.getClass().getSimpleName(), error.getCode(), e);
            return resolvable.getDefaultMessage();
        }
    }
}
