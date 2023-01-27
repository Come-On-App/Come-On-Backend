package com.comeon.backend.common.util;

import com.comeon.backend.common.api.ErrorResponse;
import com.comeon.backend.common.exception.ErrorCode;
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
