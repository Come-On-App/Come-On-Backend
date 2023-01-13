package com.comeon.backend.user.infrastructure.feign.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // snake_case json to camelCase dto
public class KakaoUserInfoResponse {

    private Long id;
    private Boolean hasSignedUp;
    private LocalDateTime connectedAt;
    private LocalDateTime syncedAt;
    private Map<String, Object> properties;
    private Map<String, Object> kakaoAccount;
}
