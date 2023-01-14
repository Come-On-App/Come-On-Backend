package com.comeon.backend.common.api;

import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
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
        log.error("Valid Fail", e);

        List<ErrorResponse.ValidError> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.ValidError(fieldError, getMessage(fieldError)))
                .collect(Collectors.toList());
        errors.addAll(
                e.getBindingResult().getGlobalErrors().stream()
                .map(objectError -> new ErrorResponse.ValidError(getMessage(objectError)))
                .collect(Collectors.toList())
        );

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
