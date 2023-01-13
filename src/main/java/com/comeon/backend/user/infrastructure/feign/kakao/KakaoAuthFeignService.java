package com.comeon.backend.user.infrastructure.feign.kakao;

import com.comeon.backend.common.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthFeignService {

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String kakaoAuthorizationGrantType;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final KakaoAuthFeignClient kakaoAuthFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public KakaoOAuthTokenResponse getToken(String code) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("getKakaoToken");
        return circuitBreaker.run(
                () -> kakaoAuthFeignClient.getToken(
                        kakaoAuthorizationGrantType,
                        kakaoClientId,
                        kakaoClientSecret,
                        kakaoRedirectUri,
                        code
                ),
                throwable -> {
                    throw (RestApiException) throwable;
                }
        );
    }
}
