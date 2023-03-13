package com.comeon.backend.meeting.presentation.api.meeting.v1;

import com.comeon.backend.common.response.ErrorResponse;
import com.comeon.backend.common.response.ResponseEntityUtils;
import com.comeon.backend.meeting.command.domain.MemberAlreadyJoinedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class MeetingExceptionHandler {

    @ExceptionHandler(MemberAlreadyJoinedException.class)
    public ResponseEntity<ErrorResponse> memberAlreadyJoinedErrorHandle(MemberAlreadyJoinedException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntityUtils.buildResponseByErrorCode(e.getErrorCode());
    }
}
