package com.comeon.backend.meeting.command.infra;

import com.comeon.backend.meeting.command.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.comeon.backend.meeting.command.domain.QMeetingEntryCode.meetingEntryCode;
import static com.comeon.backend.meeting.command.domain.QMeetingPlace.meetingPlace;

@Component
@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final MeetingJpaRepository meetingJpaRepository;
    private final MeetingMemberJpaRepository meetingMemberJpaRepository;
    private final MeetingEntryCodeJpaRepository meetingEntryCodeJpaRepository;
    private final MeetingPlaceJpaRepository meetingPlaceJpaRepository;

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        return meetingJpaRepository.save(meeting);
    }

    @Override
    public Optional<Meeting> findMeetingBy(Long meetingId) {
        return meetingJpaRepository.findById(meetingId);
    }

    @Override
    public Optional<Meeting> findMeetingBy(String entryCode) {
        Meeting meeting = jpaQueryFactory.select(meetingEntryCode.meeting)
                .from(meetingEntryCode)
                .leftJoin(meetingEntryCode.meeting, QMeeting.meeting)
                .where(meetingEntryCode.code.eq(entryCode))
                .fetchOne();

        return Optional.ofNullable(meeting);
    }

    @Override
    public MeetingMember saveMeetingMember(MeetingMember meetingMember) {
        return meetingMemberJpaRepository.save(meetingMember);
    }

    @Override
    public Optional<MeetingMember> findMeetingMemberBy(Long meetingId, Long userId) {
        return meetingMemberJpaRepository.findByMeeting_IdAndUserId(meetingId, userId);
    }

    @Override
    public Optional<MeetingEntryCode> findEntryCodeBy(Long meetingId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(meetingEntryCode)
                        .where(meetingEntryCode.meeting.id.eq(meetingId))
                        .fetchOne()
        );
    }

    @Override
    public MeetingEntryCode saveEntryCode(MeetingEntryCode entryCode) {
        return meetingEntryCodeJpaRepository.save(entryCode);
    }

    @Override
    public MeetingEntryCode saveEntryCodeAndFlush(MeetingEntryCode entryCode) {
        return meetingEntryCodeJpaRepository.saveAndFlush(entryCode);
    }

    @Override
    public MeetingPlace savePlace(MeetingPlace meetingPlace) {
        return meetingPlaceJpaRepository.save(meetingPlace);
    }

    @Override
    public int getPlaceCountBy(Long meetingId) {
        return jpaQueryFactory
                .select(meetingPlace.id.count())
                .from(meetingPlace)
                .where(meetingPlace.meeting.id.eq(meetingId))
                .fetchOne()
                .intValue();
    }
}
