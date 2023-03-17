package com.comeon.backend.admin.presentation;

import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/meetings/{meetingId}/members")
public class MeetingMemberQueryController {

    private final MeetingMemberDao meetingMemberDao;

    @GetMapping
    public MemberDetails myMemberInfo(
            @PathVariable Long meetingId,
            @RequestParam Long userId
    ) {
        return meetingMemberDao.findMemberDetails(meetingId, userId);
    }
}
