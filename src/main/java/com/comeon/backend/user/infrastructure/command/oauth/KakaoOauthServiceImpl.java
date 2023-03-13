package com.comeon.backend.user.infrastructure.command.oauth;

import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.user.command.domain.KakaoOauthService;
import com.comeon.backend.user.command.domain.OauthUserInfo;
import com.comeon.backend.user.infrastructure.command.oauth.feign.response.KakaoOauthTokenResponse;
import com.comeon.backend.user.infrastructure.command.oauth.feign.response.KakaoUserInfoResponse;
import com.comeon.backend.user.infrastructure.command.oauth.feign.KakaoApiFeignClient;
import com.comeon.backend.user.infrastructure.command.oauth.feign.KakaoAuthFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoOauthServiceImpl implements KakaoOauthService {

    private final KakaoOauthProperties kakaoOauthProperties;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final KakaoAuthFeignClient kakaoAuthFeignClient;
    private final KakaoApiFeignClient kakaoApiFeignClient;

    @Override
    public OauthUserInfo getUserInfoByCode(String code) {
        CircuitBreaker getKakaoTokenCb = circuitBreakerFactory.create("getKakaoToken");
        KakaoOauthTokenResponse tokenResponse = getKakaoTokenCb.run(
                () -> kakaoAuthFeignClient.getToken(
                        kakaoOauthProperties.getAuthorizationGrantType(),
                        kakaoOauthProperties.getClientId(),
                        kakaoOauthProperties.getClientSecret(),
                        kakaoOauthProperties.getRedirectUri(),
                        code
                ),
                throwable -> {
                    throw (RestApiException) throwable;
                }
        );

        CircuitBreaker getKakaoUSerInfoCb = circuitBreakerFactory.create("getKakaoUserInfo");
        KakaoUserInfoResponse userInfoResponse = getKakaoUSerInfoCb.run(
                () -> kakaoApiFeignClient.getUserInfo(
                        "Bearer " + tokenResponse.getAccessToken(),
                        false,
                        null
                ),
                throwable -> {
                    throw (RestApiException) throwable;
                }
        );

        return OauthUserInfo.ofKakao(
                String.valueOf(userInfoResponse.getId()),
                (String) userInfoResponse.getKakaoAccount().get("email"),
                (String) ((Map<String, Object>) userInfoResponse.getKakaoAccount().get("profile")).get("nickname")
        );
    }
}
