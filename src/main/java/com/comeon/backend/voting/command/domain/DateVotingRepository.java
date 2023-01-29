package com.comeon.backend.voting.command.domain;

import java.time.LocalDate;
import java.util.Optional;

public interface DateVotingRepository {

    DateVoting save(DateVoting dateVoting);
    Optional<DateVoting> findDateVotingBy(Long meetingId, Long userId, LocalDate date);
    void deleteDateVotingBy(Long meetingId, Long userId, LocalDate date);
    void deleteDateVotingBy(DateVoting dateVoting);
}
