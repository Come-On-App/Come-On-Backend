package com.comeon.backend.meetingmember.infrastructure;

import com.comeon.backend.meetingmember.command.domain.MeetingMember;
import com.comeon.backend.meetingmember.command.domain.MeetingMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeetingMemberRepositoryImpl implements MeetingMemberRepository {

    private final MeetingMemberJpaRepository meetingMemberJpaRepository;

    @Override
    public MeetingMember save(MeetingMember member) {
        return meetingMemberJpaRepository.save(member);
    }

    @Override
    public Optional<MeetingMember> findMember(Long meetingId, Long userId) {
        return meetingMemberJpaRepository.findByMeetingIdAndUserId(meetingId, userId);
    }

    @Override
    public List<MeetingMember> findMemberListByMeetingId(Long meetingId) {
        return meetingMemberJpaRepository.findByMeetingIdOrderByCreatedDate(meetingId);
    }

    @Override
    public void remove(MeetingMember member) {
        meetingMemberJpaRepository.delete(member);
    }
}
