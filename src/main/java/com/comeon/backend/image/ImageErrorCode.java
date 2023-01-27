package com.comeon.backend.image;

import com.comeon.backend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {
    NO_IMAGE_FILE(9000, HttpStatus.BAD_REQUEST, "업로드 요청한 파일이 비어있습니다. 원본 이미지 파일을 확인해주세요."),
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
