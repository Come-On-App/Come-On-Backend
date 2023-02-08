package com.comeon.backend.config.web.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingManager {

    private final ObjectMapper objectMapper;

    public void normalRequestLogging(RequestWrapper request) throws IOException {
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI() + request.getQueryString());

        Map<String, Object> headerMap = getHeaderMap(request);
        log.info("Request Header: {}", headerMap);

        if (isVisibleRequest(request)) {
            Map<String, Object> bodyMap = null;
            byte[] bodyByteArray = StreamUtils.copyToByteArray(request.getInputStream());
            if (bodyByteArray.length > 0) {
                bodyMap = objectMapper.readValue(bodyByteArray, Map.class);
            }
            log.info("Request Body: {}", bodyMap);
        }
    }

    private Map<String, Object> getHeaderMap(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    public void multipartRequestLogging(RequestWrapper request) throws IOException, ServletException {
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String requestPath = queryString != null ? requestURI + "?" + queryString : requestURI;
        log.info("Request: {} {}", requestMethod, requestPath);

        Map<String, Object> headerMap = getHeaderMap(request);
        log.info("Request Header: {}", headerMap);

        if (!isVisibleRequest(request)) {
            Map<String, Object> partMap = getPartMap(request);
            log.info("Request Part: {}", partMap);
        }
    }

    private Map<String, Object> getPartMap(HttpServletRequest request) throws ServletException, IOException {
        Map<String, Object> partMap = new HashMap<>();
        for (Part part : request.getParts()) {
            String content;
            if (isImage(part)) {
                content = part.getContentType();
            } else {
                byte[] partByteArray = StreamUtils.copyToByteArray(part.getInputStream());
                content = "value(" + new String(partByteArray) + ")";
            }
            partMap.put(part.getName(), content);
        }
        return partMap;
    }

    private boolean isImage(Part part) {
        String partContentType = part.getContentType();
        if (partContentType != null && partContentType.startsWith("image/")) {
            return true;
        }
        return false;
    }

    public void responseLogging(ResponseWrapper response, long executeTime) throws IOException {
        String bodyString = null;
        byte[] bodyByteArray = StreamUtils.copyToByteArray(response.getContentInputStream());
        if (bodyByteArray.length > 0) {
            bodyString = new String(bodyByteArray);
        }
        log.info("Response Body: {}", bodyString);
        log.info("took {} ms", executeTime);
        response.copyBodyToResponse();
    }

    public boolean isVisibleRequest(HttpServletRequest request) {
        final List<MediaType> visibleTypes = Arrays.asList(
                MediaType.valueOf("text/*"),
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+json"),
                MediaType.valueOf("application/*+xml")
        );

        String contentType = request.getContentType();

        if (contentType == null) {
            return true;
        }

        MediaType requestContentType = MediaType.valueOf(contentType);
        return visibleTypes.stream()
                .anyMatch(mediaType -> mediaType.includes(requestContentType));
    }
}
