package com.comeon.backend.meeting.command.domain;

public interface UserLockRemoveSupporter {

    void removeAllLockByUserId(Long userId);
}
