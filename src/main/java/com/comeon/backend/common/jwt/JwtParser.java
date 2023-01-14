package com.comeon.backend.common.jwt;

import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtParser {

    @Value("${jwt.refresh-token.reissue-criteria}")
    private long rtkReissueCriteria;

    private final ObjectMapper objectMapper;

    public JwtClaims parse(String token) {
        String[] split = token.split("\\.");
        String jwtPayload = split[1];
        String payload = decodePayload(jwtPayload);
        Map<String, Object> payloadMap;
        try {
            payloadMap = objectMapper.readValue(payload, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RestApiException(e, CommonErrorCode.INTERNAL_SERVER_ERROR); // TODO 오류 처리
        }
        return new JwtClaims(payloadMap);
    }

    private String decodePayload(String jwtPayload) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(jwtPayload));
    }

    public boolean isRtkSatisfiedAutoReissueCondition(String refreshToken) {
        Instant expiration = parse(refreshToken).getExpiration();
        Instant now = Instant.now();
        long remainSec = Duration.between(now, expiration).toSeconds();
        return remainSec <= rtkReissueCriteria;
    }
}
