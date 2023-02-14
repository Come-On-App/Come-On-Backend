package com.comeon.backend.meetingmember.command.domain;

import java.util.List;
import java.util.Optional;

public interface MeetingMemberRepository {

    MeetingMember save(MeetingMember member);
    Optional<MeetingMember> findMember(Long meetingId, Long userId);
    List<MeetingMember> findMemberListByMeetingId(Long meetingId);
    void remove(MeetingMember member);
}
