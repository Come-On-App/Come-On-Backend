package com.comeon.backend.api.error.controller;

import com.comeon.backend.common.error.*;
import com.comeon.backend.common.response.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ErrorRestDocsController {

    // 오류 응답 형식
    @GetMapping("/error/response")
    public ErrorResponse normalErrorResponse() {
        CommonErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorDescription(errorCode.getDescription())
                .build();
    }

    // 오류 응답 형식
    @GetMapping("/error/valid-error")
    public ErrorResponse validErrorResponse() {
        CommonErrorCode errorCode = CommonErrorCode.REQUEST_VALIDATION_FAIL;
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorDescription(errorCode.getDescription())
                .errors(new ArrayList<>(Collections.singleton(new ErrorResponse.ValidError("오류 발생"))))
                .build();
    }

    // 오류 코드 - 공통
    @GetMapping("/error/code/common")
    public Map<Integer, String> commonErrorCodes() {
        return Arrays.stream(CommonErrorCode.values())
                .collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::getDescription));
    }

    // 오류 코드 - 유저 관련
    @GetMapping("/error/code/users")
    public Map<Integer, String> userErrorCodes() {
        return Arrays.stream(UserErrorCode.values())
                .collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::getDescription));
    }

    @GetMapping("/error/code/image")
    public Map<Integer, String> imageErrorCodes() {
        return Arrays.stream(ImageErrorCode.values())
                .collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::getDescription));
    }

    // 오류 코드 - 모임 관련
    @GetMapping("/error/code/meetings")
    public Map<Integer, String> meetingErrorCodes() {
        return Arrays.stream(MeetingErrorCode.values())
                .collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::getDescription));
    }

    // 오류 코드 - JWT 관련
    @GetMapping("/error/code/jwt")
    public Map<Integer, String> jwtErrorCodes() {
        return Arrays.stream(JwtErrorCode.values())
                .collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::getDescription));
    }
}
