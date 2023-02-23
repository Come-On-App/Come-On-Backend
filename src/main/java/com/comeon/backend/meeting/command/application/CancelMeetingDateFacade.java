package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CancelMeetingDateFacade {

    private final MeetingRepository meetingRepository;

    public void cancelMeetingDate(Long meetingId) {
        meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId))
                .cancelMeetingDate();
    }
}
