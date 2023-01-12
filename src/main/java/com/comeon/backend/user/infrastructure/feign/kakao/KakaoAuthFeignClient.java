package com.comeon.backend.user.infrastructure.feign.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-auth-client", url = "https://kauth.kakao.com")
public interface KakaoAuthFeignClient {

    @PostMapping("/oauth/token")
    KakaoOAuthTokenResponse getToken(@RequestParam(value = "grant_type") String grantType,
                                     @RequestParam(value = "client_id") String clientId,
                                     @RequestParam(value = "client_secret") String clientSecret,
                                     @RequestParam(value = "redirect_uri") String redirectUri,
                                     @RequestParam(value = "code") String code);

}