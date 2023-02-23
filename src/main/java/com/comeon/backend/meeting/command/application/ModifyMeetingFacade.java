package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.application.dto.MeetingModifyRequest;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyMeetingFacade {

    private final MeetingRepository meetingRepository;

    public void modifyMeetingInfo(Long meetingId, MeetingModifyRequest request) {
        meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId))
                .modifyMeetingInfo(request.toMeetingInfo());
    }
}
