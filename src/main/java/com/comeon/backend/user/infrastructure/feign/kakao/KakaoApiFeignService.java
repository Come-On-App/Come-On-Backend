package com.comeon.backend.user.infrastructure.feign.kakao;

import com.comeon.backend.common.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiFeignService {

    private final KakaoApiFeignClient kakaoApiFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public KakaoUserInfoResponse getUserInfo(String kakaoToken) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("getKakaoUserInfo");
        return circuitBreaker.run(
                () -> kakaoApiFeignClient.getUserInfo(
                        "Bearer " + kakaoToken,
                        false,
                        null
                ),
                throwable -> {
                    throw (RestApiException) throwable;
                }
        );
    }
}
