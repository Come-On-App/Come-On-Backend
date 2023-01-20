package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.jwt.JwtGenerator;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.command.application.Tokens;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.infrastructure.repository.UserJpaRepository;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;
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
public class TestJwtController {

    private final JwtGenerator jwtGenerator;
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
        List<AppAuthTokensResponse> result;
        if (request.getUserIds().isEmpty()) {
            result = userRepository.findAll().stream()
                    .map(user -> new Tokens(
                            jwtGenerator.initBuilder(TokenType.ACCESS)
                                    .userId(user.getId())
                                    .nickname(user.getNickname())
                                    .authorities(user.getRole().getValue())
                                    .build(),
                            jwtGenerator.initBuilder(TokenType.REFRESH)
                                    .build()
                    ))
                    .map(AppAuthTokensResponse::generateResponse)
                    .collect(Collectors.toList());
        } else {
            result = userRepository.findByIdIn(request.userIds).stream()
                    .map(user -> new Tokens(
                            jwtGenerator.initBuilder(TokenType.ACCESS)
                                    .userId(user.getId())
                                    .nickname(user.getNickname())
                                    .authorities(user.getRole().getValue())
                                    .build(),
                            jwtGenerator.initBuilder(TokenType.REFRESH)
                                    .build()
                    ))
                    .map(AppAuthTokensResponse::generateResponse)
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
        private List<AppAuthTokensResponse> result;

        public GetTokensResponse(List<AppAuthTokensResponse> result) {
            this.count = result.size();
            this.result = result;
        }
    }
}
