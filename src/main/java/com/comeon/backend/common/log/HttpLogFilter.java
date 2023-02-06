package com.comeon.backend.common.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLogFilter extends OncePerRequestFilter {

    private final LoggingManager loggingManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = createTraceId();
        MDC.put("traceId", traceId);

        long startTime = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        if (isAsyncDispatch(request) || !loggingManager.isVisibleRequest(request)) {
            filterChain.doFilter(request, responseWrapper);
        } else {
            // 요청 로깅
            RequestWrapper requestWrapper = new RequestWrapper(request);
            loggingManager.normalRequestLogging(requestWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);
        }
        // 응답 로깅
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        loggingManager.responseLogging(responseWrapper, executeTime);

        MDC.clear();
    }

    private String createTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    protected void doFilterWrapped(RequestWrapper request, ResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            // 요청 로깅
            loggingManager.normalRequestLogging(request);
            filterChain.doFilter(request, response);
        } finally {
            // 응답 로깅
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            loggingManager.responseLogging(response, executeTime);
        }
    }
}
