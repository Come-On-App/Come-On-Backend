package com.comeon.backend.user.infrastructure.command.oauth;

import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.user.command.domain.GoogleOauthService;
import com.comeon.backend.user.command.domain.OauthUserInfo;
import com.comeon.backend.user.infrastructure.command.oauth.feign.GoogleAuthFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOauthServiceImpl implements GoogleOauthService {

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final GoogleAuthFeignClient googleAuthFeignClient;

    @Override
    public OauthUserInfo getUserInfoByIdToken(String idToken) {
        CircuitBreaker getGoogleUserInfoCb = circuitBreakerFactory.create("getGoogleUserInfo");
        Map<String, Object> payload = getGoogleUserInfoCb.run(
                () -> googleAuthFeignClient.verifyIdToken(idToken),
                throwable -> {
                    throw (RestApiException) throwable;
                }
        );

        return OauthUserInfo.ofGoogle(
                (String) payload.get("sub"),
                (String) payload.get("email"),
                (String) payload.get("name")
        );
    }
}
