package com.comeon.backend.place.infrastructure;

import com.comeon.backend.place.command.domain.MeetingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingPlaceJpaRepository extends JpaRepository<MeetingPlace, Long> {

    List<MeetingPlace> findByMeetingId(Long meetingId);
    Optional<MeetingPlace> findByIdAndMeetingId(Long placeId, Long meetingId);
}
