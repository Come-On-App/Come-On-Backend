package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class VotingDateFacade {

    private final MeetingRepository meetingRepository;

    public void addVoting(Long meetingId, Long userId, LocalDate date) {
        meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId))
                .addVoting(userId, date);
    }

    public void removeVoting(Long meetingId, Long userId, LocalDate date) {
        meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new MeetingNotExistException(meetingId))
                .removeVoting(userId, date);
    }
}
