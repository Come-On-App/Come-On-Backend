package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyMeetingTimeFacade {

    private final MeetingRepository meetingRepository;

    public void modifyMeetingTime(Long meetingId, LocalTime meetingStartTime) {
        meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId))
                .modifyMeetingTime(meetingStartTime);
    }
}
