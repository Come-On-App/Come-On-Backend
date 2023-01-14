package com.comeon.backend.user.infrastructure.feign.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "google-auth-client", url = "https://oauth2.googleapis.com", configuration = GoogleFeignErrorDecoder.class)
public interface GoogleAuthFeignClient {

    @GetMapping(value = "/tokeninfo")
    Map<String, Object> verifyIdToken(@RequestParam(value = "id_token") String idToken);
}
