package com.comeon.backend.meetingmember.infrastructure;

import com.comeon.backend.meetingmember.command.domain.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingMemberJpaRepository extends JpaRepository<MeetingMember, Long> {

    Optional<MeetingMember> findByMeetingIdAndUserId(Long meetingId, Long userId);
    List<MeetingMember> findByMeetingIdOrderByCreatedDate(Long meetingId);
}
