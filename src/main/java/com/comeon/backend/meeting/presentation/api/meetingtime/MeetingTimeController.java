package com.comeon.backend.meeting.presentation.api.meetingtime;

import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.v1.ModifyMeetingTimeFacade;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingTimeSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/meeting-time")
public class MeetingTimeController {

    private final MeetingDao meetingDao;
    private final ModifyMeetingTimeFacade modifyMeetingTimeFacade;

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping
    public MeetingTimeModifyResponse meetingTimeModify(
            @PathVariable Long meetingId,
            @Validated @RequestBody MeetingTimeModifyRequest request
    ) {
        modifyMeetingTimeFacade.modifyMeetingTime(meetingId, request.getMeetingStartTime());
        return new MeetingTimeModifyResponse();
    }

    @RequiredMemberRole
    @GetMapping
    public MeetingTimeSimple meetingTimeSimple(
            @PathVariable Long meetingId
    ) {
        return meetingDao.findMeetingTimeSimple(meetingId);
    }
}
