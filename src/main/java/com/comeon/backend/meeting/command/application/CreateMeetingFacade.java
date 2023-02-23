package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.application.dto.MeetingCreateRequest;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateMeetingFacade {

    private final MeetingRepository meetingRepository;

    public Long createMeeting(Long userId, MeetingCreateRequest request) {
        Meeting meeting = new Meeting(request.toMeetingInfo(), userId);
        return meetingRepository.save(meeting).getId();
    }
}
