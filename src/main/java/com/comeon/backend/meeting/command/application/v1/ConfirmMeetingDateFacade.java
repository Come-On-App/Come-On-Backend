package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.application.v1.dto.MeetingDateConfirmRequest;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfirmMeetingDateFacade {

    private final MeetingRepository meetingRepository;

    public void confirmMeetingDate(Long meetingId, MeetingDateConfirmRequest request) {
        meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId))
                .confirmMeetingDate(request.getMeetingDateStartFrom(), request.getMeetingDateEndTo());
    }
}
