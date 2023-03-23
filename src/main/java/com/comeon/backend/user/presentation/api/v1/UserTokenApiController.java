package com.comeon.backend.user.presentation.api.v1;

import com.comeon.backend.common.jwt.JwtManager;
import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.user.presentation.api.v1.dto.UserTokenReissueRequest;
import com.comeon.backend.user.presentation.api.v1.dto.UserTokenResponse;
import com.comeon.backend.user.query.UserDao;
import com.comeon.backend.user.query.UserSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/reissue")
public class UserTokenApiController {

    private final JwtManager jwtManager;
    private final UserDao userDao;

    @PostMapping
    public UserTokenResponse userTokenReissue(@Validated @RequestBody UserTokenReissueRequest request) {
        Long userId = jwtManager.getUserIdByRtk(request.getRefreshToken());
        UserSimple userSimple = userDao.findUserSimple(userId);

        JwtToken atk = jwtManager.buildAtk(
                userSimple.getUserId(),
                userSimple.getNickname(),
                userSimple.getRole()
        );

        JwtToken rtk = null;
        if (jwtManager.isSatisfiedRtkReissueCond(request.getRefreshToken())
                || request.getReissueRefreshTokenAlways()) {
            rtk = jwtManager.buildRtk(userSimple.getUserId());
        }

        return UserTokenResponse.create(atk, rtk);
    }
}
