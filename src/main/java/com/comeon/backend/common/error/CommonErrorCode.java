package com.comeon.backend.common.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    /**
     * 공통 에러 코드 형식 : 1xxx
     */
    INTERNAL_SERVER_ERROR(1000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다. 오류 문의 부탁드립니다."),
    INVALID_JSON(1001, HttpStatus.BAD_REQUEST, "요청 바디에 입력된 JSON 데이터 형식이 잘못되었습니다."),
    REQUEST_VALIDATION_FAIL(1002, HttpStatus.BAD_REQUEST, "요청 데이터 검증에 실패하였습니다. API 스펙을 확인해주세요."),
    HTTP_METHOD_NOT_SUPPORTED(1003, HttpStatus.METHOD_NOT_ALLOWED, "해당 HTTP METHOD는 지원하지 않습니다. API 스펙을 확인해주세요."),
    BIND_ERROR(1004, HttpStatus.BAD_REQUEST, "파라미터로 입력된 데이터 형식이 잘못되었습니다."),
    PART_NOT_PRESENT(1005, HttpStatus.BAD_REQUEST, "필수 요청값인 파트가 존재하지 않습니다."),

    UNAUTHORIZED(1100, HttpStatus.UNAUTHORIZED, "인증된 사용자만이 이용할 수 있습니다."),
    NO_AUTHORITIES(1101, HttpStatus.FORBIDDEN, "해당 요청에 필요한 권한이 부족합니다."),
    NO_AUTHORIZATION_HEADER(1102, HttpStatus.UNAUTHORIZED, "인증 헤더를 찾을 수 없습니다."),
    INVALID_AUTHORIZATION_HEADER(1103, HttpStatus.UNAUTHORIZED, "엑세스 토큰이 유효하지 않습니다."),
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
