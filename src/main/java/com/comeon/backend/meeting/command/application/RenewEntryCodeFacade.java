package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.application.dto.EntryCodeRenewResponse;
import com.comeon.backend.meeting.command.domain.EntryCode;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RenewEntryCodeFacade {

    private final MeetingRepository meetingRepository;

    public EntryCodeRenewResponse renewEntryCode(Long meetingId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        EntryCode entryCode = meeting.renewEntryCode();

        return new EntryCodeRenewResponse(meeting.getId(), entryCode);
    }
}
