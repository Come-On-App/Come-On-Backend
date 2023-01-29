package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepository {

    private final MeetingJpaRepository meetingJpaRepository;

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        return meetingJpaRepository.save(meeting);
    }

    @Override
    public Optional<Meeting> findMeetingBy(Long meetingId) {
        return meetingJpaRepository.findByIdFetchAll(meetingId);
    }

    @Override
    public Optional<Meeting> findMeetingBy(String entryCode) {
        return meetingJpaRepository.findByEntryCodeFetchAll(entryCode);
    }

    @Override
    public void flush() {
        meetingJpaRepository.flush();
    }
}
