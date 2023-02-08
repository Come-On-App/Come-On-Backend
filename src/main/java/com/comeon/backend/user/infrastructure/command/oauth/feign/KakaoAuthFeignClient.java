package com.comeon.backend.user.infrastructure.command.oauth.feign;

import com.comeon.backend.user.infrastructure.command.oauth.feign.response.KakaoOauthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-auth-client", url = "https://kauth.kakao.com", configuration = KakaoAuthFeignErrorDecoder.class)
public interface KakaoAuthFeignClient {

    @PostMapping("/oauth/token")
    KakaoOauthTokenResponse getToken(@RequestParam(value = "grant_type") String grantType,
                                     @RequestParam(value = "client_id") String clientId,
                                     @RequestParam(value = "client_secret") String clientSecret,
                                     @RequestParam(value = "redirect_uri") String redirectUri,
                                     @RequestParam(value = "code") String code);

}
