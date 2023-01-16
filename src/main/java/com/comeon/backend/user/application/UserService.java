package com.comeon.backend.user.application;

import com.comeon.backend.user.domain.User;
import com.comeon.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetails findUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return new UserDetails(user);
    }

    @Transactional
    public void modifyUser(Long userId, String nickname, String profileImageUrl) {
        User user = userRepository.findById(userId).orElseThrow();
        user.updateNickname(nickname);
        user.updateProfileImage(profileImageUrl);
    }
}
