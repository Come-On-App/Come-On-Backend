package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.command.domain.Meeting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MeetingJpaRepository extends JpaRepository<Meeting, Long> {

    @EntityGraph(attributePaths = {"entryCode", "members"})
    @Query("select m from Meeting m where m.id = :meetingId")
    Optional<Meeting> findByIdFetchAll(Long meetingId);

    @EntityGraph(attributePaths = {"members"})
    @Query("select m from Meeting m join fetch m.entryCode ec where ec.code = :code")
    Optional<Meeting> findByEntryCodeFetchAll(String code);
}
