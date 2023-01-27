package com.comeon.backend.meeting.infrastructure.domain;

import com.comeon.backend.meeting.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepository {

    private final EntityManager em;
    private final MeetingJpaRepository meetingJpaRepository;
    private final MeetingMemberJpaRepository meetingMemberJpaRepository;

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        return meetingJpaRepository.save(meeting);
    }

    @Override
    public Optional<Meeting> findMeetingBy(Long meetingId) {
        return meetingJpaRepository.findByIdFetchEntryCode(meetingId);
    }

    @Override
    public Optional<Meeting> findMeetingBy(String entryCode) {
        return meetingJpaRepository.findByEntryCode_CodeFetchEntryCode(entryCode);
    }

    @Override
    public Optional<MeetingMember> findMeetingMemberBy(Long meetingId, Long userId) {
        return meetingMemberJpaRepository.findByMeeting_IdAndUserId(meetingId, userId);
    }

    @Override
    public Optional<Meeting> findMeetingFetchPlacesBy(Long meetingId) {
        return meetingJpaRepository.findByIdFetchPlaces(meetingId);
    }

    @Override
    public void flush() {
        em.flush();
    }
}
