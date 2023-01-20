package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.command.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJpaRepository extends JpaRepository<Meeting, Long> {
}
