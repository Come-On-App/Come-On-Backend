package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingFacade {

    private final MeetingRepository meetingRepository;

    public Long addMeeting(Long userId, MeetingCommandDto.AddRequest request) {
        Meeting meeting = request.toEntity(userId);
        return meetingRepository.saveMeeting(meeting).getId();
    }

    public MeetingCommandDto.JoinResponse joinMeeting(Long userId, MeetingCommandDto.JoinRequest request) {
        Meeting meeting = meetingRepository.findMeetingBy(request.getEntryCode())
                .orElseThrow(EntryCodeNotMatchedException::new);
        Member member = meeting.join(userId);
        meetingRepository.flush();

        return new MeetingCommandDto.JoinResponse(member);
    }

    public MeetingCommandDto.RenewEntryCodeResponse renewEntryCode(Long meetingId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId));
        EntryCode entryCode = meeting.renewEntryCodeAndGet();

        return new MeetingCommandDto.RenewEntryCodeResponse(entryCode);
    }
}
