package com.comeon.backend.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(String oauthId, OauthProvider provider, String email, String name) {
        User user = userRepository.findByOauthIdAndProvider(oauthId, provider)
                .orElse(
                        User.builder()
                                .oauthId(oauthId)
                                .provider(provider)
                                .email(email)
                                .name(name)
                                .build()
                );

        if (user.getId() == null) {
            userRepository.save(user);
        } else {
            user.updateEmail(email);
            user.updateName(name);
        }
        return user;
    }
}
