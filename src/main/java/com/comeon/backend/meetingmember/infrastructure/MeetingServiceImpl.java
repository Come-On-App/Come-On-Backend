package com.comeon.backend.meetingmember.infrastructure;

import com.comeon.backend.meeting.query.application.MeetingQueryService;
import com.comeon.backend.meetingmember.command.domain.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingQueryService meetingQueryService;

    @Override
    public Long getMeetingIdByEntryCode(String entryCode) {
        return meetingQueryService.findMeetingId(entryCode);
    }
}
