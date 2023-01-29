package com.comeon.backend.user.infrastructure.command.domain.oauth.feign;

import com.comeon.backend.user.infrastructure.command.domain.oauth.feign.response.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com", configuration = KakaoApiFeignErrorDecoder.class)
public interface KakaoApiFeignClient {

    @GetMapping(value = "/v2/user/me", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoUserInfoResponse getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
                                      @RequestParam(value = "secure_resource") Boolean secureResource,
                                      @RequestParam(value = "property_keys") String[] propertyKeys);
}
