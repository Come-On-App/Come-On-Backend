package com.comeon.backend.date.command.domain.voting;

import java.time.LocalDate;
import java.util.Optional;

public interface DateVotingRepository {

    DateVoting save(DateVoting dateVoting);
    Optional<DateVoting> findDateVotingBy(Long meetingId, Long userId, LocalDate date);
    void deleteDateVotingBy(Long meetingId, Long userId, LocalDate date);
    void deleteDateVotingBy(DateVoting dateVoting);
}
