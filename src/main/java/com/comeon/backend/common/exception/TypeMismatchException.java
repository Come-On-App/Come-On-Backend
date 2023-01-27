package com.comeon.backend.common.exception;

public class TypeMismatchException extends RestApiException {


    public TypeMismatchException() {
        super(CommonErrorCode.BIND_ERROR);
    }

    public TypeMismatchException(String message) {
        super(message, CommonErrorCode.BIND_ERROR);
    }

    public TypeMismatchException(Throwable cause) {
        super(cause, CommonErrorCode.BIND_ERROR);
    }

    public TypeMismatchException(String message, Throwable cause) {
        super(message, cause, CommonErrorCode.BIND_ERROR);
    }
}
