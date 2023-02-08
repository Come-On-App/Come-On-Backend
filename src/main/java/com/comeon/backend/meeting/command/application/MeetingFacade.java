package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.application.dto.EntryCodeRenewResponse;
import com.comeon.backend.meeting.command.application.dto.MeetingAddRequest;
import com.comeon.backend.meeting.command.application.dto.MeetingTimeModifyRequest;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.MeetingNotExistException;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingFacade {

    private final MeetingRepository meetingRepository;

    public Long addMeeting(Long userId, MeetingAddRequest request) {
        Meeting meeting = request.toEntity(userId);
        return meetingRepository.save(meeting).getId();
    }

    public EntryCodeRenewResponse renewEntryCode(Long meetingId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.renewEntryCode();

        return new EntryCodeRenewResponse(meeting);
    }

    public void modifyMeetingTime(Long meetingId, MeetingTimeModifyRequest request) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        meeting.modifyMeetingTime(request.getMeetingStartTime());
    }
}
