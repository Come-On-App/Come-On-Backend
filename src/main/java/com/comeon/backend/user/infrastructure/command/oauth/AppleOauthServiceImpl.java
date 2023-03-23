package com.comeon.backend.user.infrastructure.command.oauth;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.user.command.domain.AppleOauthService;
import com.comeon.backend.user.command.domain.OauthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOauthServiceImpl implements AppleOauthService {

    private final JwtManager jwtManager;

    @Override
    public OauthUserInfo getUserInfoBy(String identityToken, String user, String email, String name) {
        String userEmail = email;
        if (!StringUtils.hasText(userEmail)) {
            Map<String, Object> payload = jwtManager.parseToMap(identityToken);
            String payloadEmail = payload.get("email").toString();

            if (StringUtils.hasText(payloadEmail)) userEmail = payloadEmail;
        }

        return OauthUserInfo.ofApple(user, userEmail, name);
    }
}
