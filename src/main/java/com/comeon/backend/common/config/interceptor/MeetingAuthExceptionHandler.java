package com.comeon.backend.common.config.interceptor;

import com.comeon.backend.common.response.ErrorResponse;
import com.comeon.backend.common.response.ResponseEntityUtils;
import com.comeon.backend.meeting.query.application.NotMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(0)
@RestControllerAdvice
public class MeetingAuthExceptionHandler {

    @ExceptionHandler(MemberAuthorizationException.class)
    public ResponseEntity<ErrorResponse> memberAuthErrorHandle(MemberAuthorizationException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntityUtils.buildResponseByErrorCode(e.getErrorCode());
    }

    @ExceptionHandler(NotMemberException.class)
    public ResponseEntity<ErrorResponse> notMemberErrorHandle(NotMemberException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntityUtils.buildResponseByErrorCode(e.getErrorCode());
    }
}
