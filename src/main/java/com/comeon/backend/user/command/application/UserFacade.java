package com.comeon.backend.user.command.application;

import com.comeon.backend.user.command.application.dto.UserModifyRequest;
import com.comeon.backend.user.command.domain.LeaveMeetingService;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.command.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final LeaveMeetingService leaveMeetingService;

    public void modifyUser(Long userId, UserModifyRequest request) {
        User user = userRepository.findBy(userId).orElseThrow();
        user.updateProfile(request.getNickname(), request.getProfileImageUrl());
    }

    public void withdrawUser(Long userId) {
        User user = userRepository.findBy(userId).orElseThrow();
        leaveMeetingService.leaveMeeting(userId);
        user.withdraw();
        // logout 처리
    }
}
