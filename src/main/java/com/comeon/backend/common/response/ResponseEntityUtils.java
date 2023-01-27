package com.comeon.backend.common.response;

import com.comeon.backend.common.error.ErrorCode;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    public static ResponseEntity<ErrorResponse> buildResponseByErrorCode(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorCode(errorCode.getCode())
                                .errorDescription(errorCode.getDescription())
                                .build()
                );
    }
}
