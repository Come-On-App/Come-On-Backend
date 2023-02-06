package com.comeon.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private Integer errorCode;
    private String errorDescription;
    private List<ValidError> errors;

    @Getter
    @AllArgsConstructor
    public static class ValidError {

        private String field;
        private String message;
        private Object rejectedValue;

        public ValidError(FieldError fieldError, String message) {
            this.field = fieldError.getField();
            this.message = message;
            this.rejectedValue = fieldError.getRejectedValue();
        }

        public ValidError(String message) {
            this.field = "GLOBAL";
            this.message = message;
        }
    }
}
