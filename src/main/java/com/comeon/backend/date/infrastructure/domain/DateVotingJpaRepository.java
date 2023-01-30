package com.comeon.backend.date.infrastructure.domain;

import com.comeon.backend.date.command.domain.voting.DateVoting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DateVotingJpaRepository extends JpaRepository<DateVoting, Long> {

    Optional<DateVoting> findByMeetingIdAndUserIdAndDate(Long meetingId, Long userId, LocalDate date);
    void deleteByMeetingIdAndUserIdAndDate(Long meetingId, Long userId, LocalDate date);
}
