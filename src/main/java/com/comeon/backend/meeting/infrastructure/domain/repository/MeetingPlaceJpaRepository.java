package com.comeon.backend.meeting.infrastructure.domain.repository;

import com.comeon.backend.meeting.command.domain.MeetingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingPlaceJpaRepository extends JpaRepository<MeetingPlace, Long> {

    @Query("select mp from MeetingPlace mp " +
            "where mp.lockUserId = :userId")
    List<MeetingPlace> findLockedMeetingPlaces(@Param("userId") Long userId);
}
