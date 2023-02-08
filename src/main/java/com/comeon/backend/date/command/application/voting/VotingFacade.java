package com.comeon.backend.date.command.application.voting;

import com.comeon.backend.date.command.domain.voting.DateVotingRepository;
import com.comeon.backend.date.command.domain.voting.DateVoting;
import com.comeon.backend.date.command.domain.voting.VotingDateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class VotingFacade {

    private final DateVotingRepository dateVotingRepository;
    private final VotingDateValidator votingDateValidator;

    public void addVoting(Long userId, Long meetingId, AddVotingRequest request) {
        LocalDate date = request.getDate();
        dateVotingRepository.findDateVotingBy(meetingId, userId, date)
                .ifPresent(this::generateVotingExistError);

        checkDateInCalendar(meetingId, date);

        DateVoting dateVoting = new DateVoting(meetingId, userId, date);
        dateVotingRepository.save(dateVoting);
    }

    private void checkDateInCalendar(Long meetingId, LocalDate date) {
        if (!votingDateValidator.verifyDateInMeetingCalendar(meetingId, date)) {
            throw new DateOutOfMeetingCalendarException("일자가 투표 가능 범위에 포함되지 않습니다. meetingId: " +
                    meetingId + ", date: " + date.format(DateTimeFormatter.ISO_DATE));
        }
    }

    private void generateVotingExistError(DateVoting voting) {
        throw new DateVotingAlreadyExistException(voting);
    }

    public void removeVoting(Long userId, Long meetingId, RemoveVotingRequest request) {
        LocalDate date = request.getDate();
        checkDateInCalendar(meetingId, date);

        DateVoting dateVoting = dateVotingRepository.findDateVotingBy(meetingId, userId, date)
                .orElseThrow(
                        () -> new DateVotingNotExistException("해당 일자에 투표한 이력이 없습니다. " +
                                "meetingId: " + meetingId +
                                ", userId: " + userId +
                                ", date: " + date.format(DateTimeFormatter.ISO_DATE))
                );
        dateVotingRepository.deleteDateVotingBy(dateVoting);
    }
}
