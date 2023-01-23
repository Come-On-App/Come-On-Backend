package com.comeon.backend.auth.infra;

import com.comeon.backend.auth.application.TokenType;
import com.comeon.backend.auth.domain.JwtClaims;
import com.comeon.backend.auth.domain.JwtParser;
import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtParserImpl implements JwtParser {

    private final ObjectMapper objectMapper;

    @Override
    public JwtClaims parse(String token) {
        String[] split = token.split("\\.");
        String payloadString = decodePayload(split[1]);
        Map<String, Object> payload;
        try {
            payload = objectMapper.readValue(payloadString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RestApiException(e, CommonErrorCode.INTERNAL_SERVER_ERROR); // TODO 오류 처리
        }

        return new JwtClaims(
                TokenType.of(cast(payload.get(ClaimNames.SUBJECT), String.class)),
                cast(payload.get(ClaimNames.ISSUED_AT), Instant.class),
                cast(payload.get(ClaimNames.EXPIRATION), Instant.class),
                cast(payload.get(ClaimNames.USER_ID), Long.class),
                cast(payload.get(ClaimNames.NICKNAME), String.class),
                cast(payload.get(ClaimNames.AUTHORITIES), String.class)
        );
    }

    private String decodePayload(String jwtPayload) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(jwtPayload));
    }

    private <T> T cast(Object value, Class<T> requiredType) {
        if (value == null) {
            return null;
        }

        if (Instant.class.equals(requiredType) || Date.class.equals(requiredType)) {
            value = toMilliSecNumber(value);
            if (Date.class.equals(requiredType)) {
                value = toDate(value);
            } else {
                value = toInstant(value);
            }
        }

        if (value instanceof Integer) {
            int intValue = (Integer) value;
            if (requiredType == Long.class) {
                value = (long) intValue;
            } else if (requiredType == Short.class && intValue >= Short.MIN_VALUE && intValue <= Short.MAX_VALUE) {
                value = (short) intValue;
            } else if (requiredType == Byte.class && intValue >= Byte.MIN_VALUE && intValue <= Byte.MAX_VALUE) {
                value = (byte) intValue;
            }
        }

        if (!requiredType.isInstance(value)) {
            // TODO 예외 처리
            throw new RuntimeException();
        }

        return requiredType.cast(value);
    }

    private Object toMilliSecNumber(Object obj) {
        if (obj instanceof Number) {
            long seconds = ((Number) obj).longValue();
            obj = seconds * 1000;
        } else if (obj instanceof String) {
            long seconds = Long.parseLong((String) obj);
            obj = seconds * 1000;
        }
        return obj;
    }

    private Instant toInstant(Object obj) {
        if (obj instanceof Instant) {
            return (Instant) obj;
        } else if (obj instanceof Date) {
            return ((Date) obj).toInstant();
        } else if (obj instanceof Calendar) {
            return ((Calendar) obj).toInstant();
        } else if (obj instanceof Number) {
            long millis = ((Number) obj).longValue();
            return Instant.ofEpochMilli(millis);
        } else if (obj instanceof String) {
            return Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse((String) obj));
        } else {
            throw new IllegalStateException("Cannot create Instant value '" + obj + "'.");
        }
    }

    private Date toDate(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Date) {
            return (Date) obj;
        } else if (obj instanceof Calendar) {
            return ((Calendar) obj).getTime();
        } else if (obj instanceof Number) {
            long millis = ((Number) obj).longValue();
            return new Date(millis);
        } else {
            throw new IllegalStateException("Cannot create Date value '" + obj + "'.");
        }
    }
}
