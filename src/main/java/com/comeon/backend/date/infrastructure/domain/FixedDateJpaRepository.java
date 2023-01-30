package com.comeon.backend.date.infrastructure.domain;

import com.comeon.backend.date.command.domain.confirm.FixedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedDateJpaRepository extends JpaRepository<FixedDate, Long> {

    Optional<FixedDate> findByMeetingId(Long meetingId);
}
