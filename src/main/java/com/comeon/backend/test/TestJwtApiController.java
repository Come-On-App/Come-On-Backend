package com.comeon.backend.test;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.user.api.dto.UserTokenResponse;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.infrastructure.command.repository.UserJpaRepository;
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
        List<UserTokenResponse> result;
        if (request.getUserIds().isEmpty()) {
            result = userRepository.findAll().stream()
                    .map(user -> {
                        JwtToken atk = jwtManager.buildAtk(user.getId(), user.getNickname(), user.getRole().getValue());
                        JwtToken rtk = jwtManager.buildRtk(user.getId());
                        return UserTokenResponse.create(atk, rtk);
                    })
                    .collect(Collectors.toList());
        } else {
            result = userRepository.findByIdIn(request.userIds).stream()
                    .map(user -> {
                        JwtToken atk = jwtManager.buildAtk(user.getId(), user.getNickname(), user.getRole().getValue());
                        JwtToken rtk = jwtManager.buildRtk(user.getId());
                        return UserTokenResponse.create(atk, rtk);
                    })
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
        private List<UserTokenResponse> result;

        public GetTokensResponse(List<UserTokenResponse> result) {
            this.count = result.size();
            this.result = result;
        }
    }
}
