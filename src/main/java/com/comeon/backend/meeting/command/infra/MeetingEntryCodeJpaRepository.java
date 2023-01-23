package com.comeon.backend.meeting.command.infra;

import com.comeon.backend.meeting.command.domain.MeetingEntryCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingEntryCodeJpaRepository extends JpaRepository<MeetingEntryCode, Long> {
}
