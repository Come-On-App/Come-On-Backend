package com.comeon.backend.user.presentation.api;

import com.comeon.backend.common.jwt.JwtGenerator;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.user.application.Tokens;
import com.comeon.backend.user.domain.UserRepository;
import com.comeon.backend.user.presentation.api.response.AppAuthTokensResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Profile({"local", "dev"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/test-api/v1")
public class TestJwtController {

    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;

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
                    .map(AppAuthTokenResponseUtil::generateResponse)
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
                    .map(AppAuthTokenResponseUtil::generateResponse)
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
