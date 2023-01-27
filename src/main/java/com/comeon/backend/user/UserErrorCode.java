package com.comeon.backend.user;

import com.comeon.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    /**
     * 유저 도메인 에러 코드 형식 : 2xxx
     * 구글 관련 에러 코드 형식 : 25xx
     * 카카오 관련 에러 코드 형식 : 26xx
     */
    GOOGLE_SERVER_ERROR(2500, HttpStatus.INTERNAL_SERVER_ERROR, "구글 서버 오류입니다. 잠시후 다시 시도해주세요."),
    GOOGLE_ID_TOKEN_INVALID(2501, HttpStatus.BAD_REQUEST, "구글 id-token이 유효하지 않습니다."),
    KAKAO_SERVER_ERROR(2600, HttpStatus.INTERNAL_SERVER_ERROR, "카카오 서버 오류입니다. 잠시후 다시 시도해주세요."),
    KAKAO_CODE_INVALID(2601, HttpStatus.BAD_REQUEST, "카카오 인가 코드가 유효하지 않습니다."),
    KAKAO_ACCESS_TOKEN_INVALID(2602, HttpStatus.INTERNAL_SERVER_ERROR, "카카오 엑세스 토큰이 유효하지 않습니다. 일시적인 서버 오류일 수 있으니 다시 시도해주세요."),
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

    UserErrorCode(int code, HttpStatus httpStatus, String description) {
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
