package com.comeon.backend.meeting.command.infra;

import com.comeon.backend.meeting.command.domain.MeetingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingPlaceJpaRepository extends JpaRepository<MeetingPlace, Long> {
}
