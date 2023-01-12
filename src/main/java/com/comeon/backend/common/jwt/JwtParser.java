package com.comeon.backend.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtParser {

    private final ObjectMapper objectMapper;

    public JwtClaims parse(String token) {
        String[] split = token.split("\\.");
        String jwtPayload = split[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(jwtPayload));
        Map<String, Object> payloadMap;
        try {
            payloadMap = objectMapper.readValue(payload, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO 오류 처리
        }
        return new JwtClaims(payloadMap);
    }
}
