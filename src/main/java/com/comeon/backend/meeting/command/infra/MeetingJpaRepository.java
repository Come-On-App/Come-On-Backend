package com.comeon.backend.meeting.command.infra;

import com.comeon.backend.meeting.command.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MeetingJpaRepository extends JpaRepository<Meeting, Long> {

    @Query("select m from Meeting m join fetch m.entryCode where m.id = :meetingId")
    Optional<Meeting> findByIdFetchEntryCode(Long meetingId);

    @Query("select m from Meeting m join fetch m.entryCode ec where ec.code = :code")
    Optional<Meeting> findByEntryCode_CodeFetchEntryCode(String code);

    @Query("select distinct m from Meeting m join fetch m.places where m.id = :meetingId")
    Optional<Meeting> findByIdFetchPlaces(Long meetingId);
}
