package com.comeon.backend.voting.infrastructure.domain;

import com.comeon.backend.voting.command.domain.DateVoting;
import com.comeon.backend.voting.command.domain.DateVotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DateVotingRepositoryImpl implements DateVotingRepository {

    private final DateVotingJpaRepository dateVotingJpaRepository;

    @Override
    public DateVoting save(DateVoting dateVoting) {
        return dateVotingJpaRepository.save(dateVoting);
    }

    @Override
    public Optional<DateVoting> findDateVotingBy(Long meetingId, Long userId, LocalDate date) {
        return dateVotingJpaRepository.findByMeetingIdAndUserIdAndDate(meetingId, userId, date);
    }

    @Override
    public void deleteDateVotingBy(Long meetingId, Long userId, LocalDate date) {
        dateVotingJpaRepository.deleteByMeetingIdAndUserIdAndDate(meetingId, userId, date);
    }

    @Override
    public void deleteDateVotingBy(DateVoting dateVoting) {
        dateVotingJpaRepository.delete(dateVoting);
    }
}
