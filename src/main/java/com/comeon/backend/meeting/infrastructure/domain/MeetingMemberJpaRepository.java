package com.comeon.backend.meeting.infrastructure.domain;

import com.comeon.backend.meeting.command.domain.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingMemberJpaRepository extends JpaRepository<MeetingMember, Long> {

    Optional<MeetingMember> findByMeeting_IdAndUserId(Long meetingId, Long userId);
}
