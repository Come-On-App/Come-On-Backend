package com.comeon.backend.user.infrastructure.command.oauth;

import com.comeon.backend.common.error.CommonErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.user.command.domain.GoogleOauthService;
import com.comeon.backend.user.command.domain.OauthUserInfo;
import com.comeon.backend.user.infrastructure.command.oauth.feign.GoogleAuthFeignClient;
import io.github.resilience4j.retry.annotation.Retry;
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
    @Retry(name = "googleOauthRetry")
    public OauthUserInfo getUserInfoByIdToken(String idToken) {
        CircuitBreaker getGoogleUserInfoCb = circuitBreakerFactory.create("getGoogleUserInfo");
        Map<String, Object> payload = getGoogleUserInfoCb.run(
                () -> googleAuthFeignClient.verifyIdToken(idToken),
                throwable -> {
                    throw new RestApiException(throwable, CommonErrorCode.INTERNAL_SERVER_ERROR);
                }
        );

        return OauthUserInfo.ofGoogle(
                (String) payload.get("sub"),
                (String) payload.get("email"),
                (String) payload.get("name")
        );
    }
}
