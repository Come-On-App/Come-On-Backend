package com.comeon.backend.user.command.infra.domain.oauth.feign;

import com.comeon.backend.common.exception.CommonErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.user.UserErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoAuthFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                try {
                    Map<String, Object> result = objectMapper.readValue(
                            response.body().asReader(StandardCharsets.UTF_8),
                            new TypeReference<>() {
                            }
                    );
                    log.error("result: {}", result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                throw new RestApiException(methodKey + ": 카카오 인가코드가 유효하지 않습니다.", UserErrorCode.KAKAO_CODE_INVALID);
            case 500:
                throw new RestApiException(methodKey + ": 카카오 서버 오류가 발생하여 카카오 엑세스 토큰을 받아올 수 없습니다.", UserErrorCode.KAKAO_SERVER_ERROR);
            default:
                throw new RestApiException(methodKey + ": 카카오 엑세스 토큰 받기 오류 발생.", CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
