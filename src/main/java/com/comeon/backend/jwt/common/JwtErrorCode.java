package com.comeon.backend.jwt.common;

import com.comeon.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {

    NO_SESSION(4000, HttpStatus.UNAUTHORIZED, "로그인되지 않은 유저입니다. 로그인 후 이용해주세요."),
    INVALID_USER_ATK(4001, HttpStatus.UNAUTHORIZED, "엑세스 토큰이 유효하지 않습니다. 엑세스 토큰 재발급을 시도해주세요."),
    INVALID_USER_RTK(4002, HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다. 다시 로그인 해주세요."),
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

    JwtErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

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
