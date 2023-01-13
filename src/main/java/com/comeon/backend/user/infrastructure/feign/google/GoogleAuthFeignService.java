package com.comeon.backend.user.infrastructure.feign.google;

import com.comeon.backend.common.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleAuthFeignService {

    private final GoogleAuthFeignClient googleAuthFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public GoogleUserInfoResponse getUserInfo(String idToken) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("getGoogleUserInfo");
        Map<String, Object> payload = circuitBreaker.run(
                () -> googleAuthFeignClient.verifyIdToken(idToken),
                throwable -> {
                    throw (RestApiException) throwable;
                }
        );

        return new GoogleUserInfoResponse(
                (String) payload.get("sub"),
                (String) payload.get("email"),
                (String) payload.get("name")
        );
    }
}
