package com.comeon.backend.admin.presentation;

import com.comeon.backend.admin.presentation.dto.UserLockRemoveRequest;
import com.comeon.backend.admin.presentation.dto.UserLockRemoveResponse;
import com.comeon.backend.meeting.command.application.v2.UserLockRemoveFacade;
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
@RequestMapping("/api/v1/admin/meeting-place/unlock")
public class UserLockRemoveController {

    private final UserLockRemoveFacade userLockRemoveFacade;

    @PostMapping
    public UserLockRemoveResponse userLockRemove(@RequestBody @Validated UserLockRemoveRequest request) {
        userLockRemoveFacade.removeAllUnlockByUserId(request.getUserId());
        return new UserLockRemoveResponse();
    }
}
