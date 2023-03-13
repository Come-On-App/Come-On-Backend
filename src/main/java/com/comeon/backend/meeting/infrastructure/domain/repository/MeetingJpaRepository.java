package com.comeon.backend.meeting.infrastructure.domain.repository;

import com.comeon.backend.meeting.command.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MeetingJpaRepository extends JpaRepository<Meeting, Long> {

    @Query("select m from Meeting m " +
            "left join fetch m.meetingDate md " +
            "left join fetch m.dateVotingList dvl " +
            "where m.id = :meetingId")
    Optional<Meeting> findByIdFetchVotingDates(@Param("meetingId") Long meetingId);

    @Query("select m from Meeting m " +
            "left join fetch m.meetingDate md " +
            "left join fetch m.dateVotingList dvl " +
            "where m.entryCode.code = :entryCode")
    Optional<Meeting> findByEntryCodeFetchVotingDates(@Param("entryCode") String entryCode);
}
