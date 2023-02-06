package com.comeon.backend.image.application;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.ImageErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class FileEmptyException extends RestApiException {

    private static final ErrorCode errorCode = ImageErrorCode.NO_IMAGE_FILE;

    public FileEmptyException() {
        super(errorCode);
    }
}
