package com.comeon.backend.meeting.command.application.v2;

import com.comeon.backend.meeting.command.domain.UserLockRemoveSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLockRemoveFacade {

    private final UserLockRemoveSupporter userLockRemoveSupporter;

    public void removeAllUnlockByUserId(Long userId) {
        userLockRemoveSupporter.removeAllLockByUserId(userId);
    }
}
