package com.comeon.backend.test;

import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.infrastructure.command.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO 추후 어드민 기능으로 변환
@Slf4j
@Profile({"local", "dev"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/test-api/v1")
public class TestApiController {

    private final TestJwtBuilder testJwtBuilder;
    private final UserJpaRepository userRepository;

    // 전체 유저 리스트 조회
    @GetMapping("/users")
    public GetAllUserSimpleResponse getAllUserSimple() {
        return new GetAllUserSimpleResponse(
                userRepository.findAll().stream()
                        .map(user -> new UserSimple(user.getId(), user.getName(), user.getNickname()))
                        .collect(Collectors.toList())
        );
    }

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
        Long atkExpirationSec = request.getAtkExpirationSec();
        Long rtkExpirationSec = request.getRtkExpirationSec();
        List<UserTokenSimple> result;
        if (request.getUserIds().isEmpty()) {
            result = userRepository.findAll().stream()
                    .map(user -> {
                        JwtToken atk = testJwtBuilder.buildAtk(user.getId(), user.getNickname(), user.getRole().getValue(), atkExpirationSec);
                        JwtToken rtk = testJwtBuilder.buildRtk(user.getId(), rtkExpirationSec);
                        return new UserTokenSimple(atk, rtk);
                    })
                    .collect(Collectors.toList());
        } else {
            result = userRepository.findByIdIn(request.userIds).stream()
                    .map(user -> {
                        JwtToken atk = testJwtBuilder.buildAtk(user.getId(), user.getNickname(), user.getRole().getValue(), atkExpirationSec);
                        JwtToken rtk = testJwtBuilder.buildRtk(user.getId(), rtkExpirationSec);
                        return new UserTokenSimple(atk, rtk);
                    })
                    .collect(Collectors.toList());
        }
        return new GetTokensResponse(result);
    }

    @Getter
    @AllArgsConstructor
    public static class GetAllUserSimpleResponse {
        private List<UserSimple> userSimples;
    }

    @Getter
    @AllArgsConstructor
    public static class UserSimple {
        private Long userId;
        private String name;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTokensRequest {
        private List<Long> userIds = new ArrayList<>();
        private Long atkExpirationSec;
        private Long rtkExpirationSec;
    }

    @Getter
    @AllArgsConstructor
    public static class GetTokensResponse {
        private List<UserTokenSimple> result;
    }

    @Getter
    @AllArgsConstructor
    public static class UserTokenSimple {
        private AtkSimple accessToken;
        private RtkSimple refreshToken;

        public UserTokenSimple(JwtToken atk, JwtToken rtk) {
            this.accessToken = new AtkSimple(atk.getToken(), atk.getPayload().getExpiration().toEpochMilli(), atk.getPayload().getUserId());
            this.refreshToken = new RtkSimple(rtk.getToken(), rtk.getPayload().getExpiration().toEpochMilli());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class AtkSimple {
        private String token;
        private Long expiry;
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class RtkSimple {
        private String token;
        private Long expiry;
    }
}
