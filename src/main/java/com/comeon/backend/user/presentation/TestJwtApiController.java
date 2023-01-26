package com.comeon.backend.user.presentation;

import com.comeon.backend.jwt.application.JwtManager;
import com.comeon.backend.jwt.application.JwtToken;
import com.comeon.backend.user.command.application.dto.LoginDto;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.command.infra.domain.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Profile({"local", "dev"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/test-api/v1")
public class TestJwtApiController {

    private final JwtManager jwtManager;
    private final UserJpaRepository userRepository;

    @PostMapping("/users")
    public String generateUsers() {
        int count = 10 - userRepository.findAll().size();
        if (count > 0) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                users.add(
                        new User(String.valueOf(1234 + i), OauthProvider.KAKAO, "email" + i + "@email.com", "user" + i)
                );
            }
            userRepository.saveAll(users);
        }
        return "OK";
    }

    @PostMapping("/tokens")
    public GetTokensResponse getTokens(@RequestBody GetTokensRequest request) {
        List<LoginDto.OauthLoginResponse> result;
        if (request.getUserIds().isEmpty()) {
            result = userRepository.findAll().stream()
                    .map(user -> jwtManager.buildTokens(user.getId(), user.getNickname(), user.getRole().getValue()))
                    .map(tokens -> {
                                JwtToken atk = tokens.getAccessToken();
                                JwtToken rtk = tokens.getRefreshToken();
                                return new LoginDto.OauthLoginResponse(
                                        new LoginDto.AccessTokenResponse(atk.getToken(), atk.getPayload().getExpiration().toEpochMilli(), atk.getPayload().getUserId()),
                                        new LoginDto.RefreshTokenResponse(rtk.getToken(), rtk.getPayload().getExpiration().toEpochMilli())
                                );
                            }
                    )
                    .collect(Collectors.toList());
        } else {
            result = userRepository.findByIdIn(request.userIds).stream()
                    .map(user -> jwtManager.buildTokens(user.getId(), user.getNickname(), user.getRole().getValue()))
                    .map(tokens -> {
                                JwtToken atk = tokens.getAccessToken();
                                JwtToken rtk = tokens.getRefreshToken();
                                return new LoginDto.OauthLoginResponse(
                                        new LoginDto.AccessTokenResponse(atk.getToken(), atk.getPayload().getExpiration().toEpochMilli(), atk.getPayload().getUserId()),
                                        new LoginDto.RefreshTokenResponse(rtk.getToken(), rtk.getPayload().getExpiration().toEpochMilli())
                                );
                            }
                    )
                    .collect(Collectors.toList());
        }
        return new GetTokensResponse(result);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTokensRequest {
        private List<Long> userIds = new ArrayList<>();
    }

    @Getter
    public static class GetTokensResponse {
        private Integer count;
        private List<LoginDto.OauthLoginResponse> result;

        public GetTokensResponse(List<LoginDto.OauthLoginResponse> result) {
            this.count = result.size();
            this.result = result;
        }
    }
}
