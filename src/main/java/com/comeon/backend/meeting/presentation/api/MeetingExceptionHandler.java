package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.api.ErrorResponse;
import com.comeon.backend.common.util.ResponseEntityUtils;
import com.comeon.backend.meeting.presentation.MemberAuthorizationException;
import com.comeon.backend.meeting.presentation.NotMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(0)
@RestControllerAdvice
public class MeetingExceptionHandler {

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
