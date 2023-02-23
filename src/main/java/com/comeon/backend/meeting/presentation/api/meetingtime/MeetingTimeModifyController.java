package com.comeon.backend.meeting.presentation.api.meetingtime;

import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.ModifyMeetingTimeFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MeetingTimeModifyController {

    private final ModifyMeetingTimeFacade modifyMeetingTimeFacade;

    // TODO 모임 시간 조회 API
    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/api/v1/meetings/{meetingId}/meeting-time")
    public MeetingTimeModifyResponse meetingTimeModify(
            @PathVariable Long meetingId,
            @Validated @RequestBody MeetingTimeModifyRequest request
    ) {
        modifyMeetingTimeFacade.modifyMeetingTime(meetingId, request.getMeetingStartTime());
        return new MeetingTimeModifyResponse();
    }
}
