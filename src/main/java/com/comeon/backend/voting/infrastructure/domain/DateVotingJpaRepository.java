package com.comeon.backend.voting.infrastructure.domain;

import com.comeon.backend.voting.command.domain.DateVoting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DateVotingJpaRepository extends JpaRepository<DateVoting, Long> {

    Optional<DateVoting> findByMeetingIdAndUserIdAndDate(Long meetingId, Long userId, LocalDate date);
    void deleteByMeetingIdAndUserIdAndDate(Long meetingId, Long userId, LocalDate date);
}
