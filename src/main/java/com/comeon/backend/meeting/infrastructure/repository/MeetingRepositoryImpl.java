package com.comeon.backend.meeting.infrastructure.repository;

import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepository {

    private final MeetingJpaRepository meetingJpaRepository;

    @Override
    public Meeting save(Meeting meeting) {
        return meetingJpaRepository.save(meeting);
    }

    @Override
    public Optional<Meeting> findMeetingBy(Long meetingId) {
        return meetingJpaRepository.findByIdFetchVotingDates(meetingId);
    }

    @Override
    public Optional<Meeting> findMeetingBy(String entryCode) {
        return meetingJpaRepository.findByEntryCodeFetchVotingDates(entryCode);
    }

    @Override
    public void flush() {
        meetingJpaRepository.flush();
    }
}
