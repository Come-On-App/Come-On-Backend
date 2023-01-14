package com.comeon.backend.common.security;

import com.comeon.backend.common.api.ErrorResponse;
import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("No Authorities", accessDeniedException);
        log.error("Request Uri : {} {}", request.getMethod(), request.getRequestURI());

        ErrorCode errorCode = CommonErrorCode.NO_AUTHORITIES;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorDescription(errorCode.getDescription())
                .build();

        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getHttpStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseBody);
    }
}
