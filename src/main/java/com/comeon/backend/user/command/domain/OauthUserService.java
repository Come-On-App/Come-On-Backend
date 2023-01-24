package com.comeon.backend.user.command.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthUserService {

    private final UserRepository userRepository;

    public User saveUser(OauthUserInfo oauthUserInfo) {
        User user = userRepository.findBy(oauthUserInfo.getOauthId(), oauthUserInfo.getProvider())
                .orElse(User.createBy(oauthUserInfo));

        if (user.getId() == null) {
            userRepository.save(user);
        } else {
            user.update(oauthUserInfo);
        }
        return user;
    }
}
