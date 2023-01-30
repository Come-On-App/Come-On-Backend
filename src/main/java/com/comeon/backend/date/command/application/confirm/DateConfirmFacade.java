package com.comeon.backend.date.command.application.confirm;

import com.comeon.backend.date.command.domain.confirm.DateRangeValidator;
import com.comeon.backend.date.command.domain.confirm.FixedDate;
import com.comeon.backend.date.command.domain.confirm.FixedDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class DateConfirmFacade {

    private final FixedDateRepository fixedDateRepository;
    private final DateRangeValidator dateRangeValidator;

    public void confirmMeetingDate(Long meetingId, FixedDateAddRequest request) {
        fixedDateRepository.findFixedDateByMeetingId(meetingId)
                .ifPresent(this::generateFixedDateExistError);

        LocalDate meetingDateStartFrom = request.getMeetingDateStartFrom();
        LocalDate meetingDateEndTo = request.getMeetingDateEndTo();
        checkDateRangeInCalendar(meetingId, meetingDateStartFrom, meetingDateEndTo);

        LocalTime meetingStartTime = request.getMeetingStartTime();
        FixedDate fixedDate = new FixedDate(meetingId, meetingDateStartFrom, meetingDateEndTo, meetingStartTime);
        fixedDateRepository.save(fixedDate);
    }

    private void checkDateRangeInCalendar(Long meetingId, LocalDate meetingDateStartFrom, LocalDate meetingDateEndTo) {
        boolean rangeVerifyResult = dateRangeValidator.verifyDateRangeInMeetingCalendar(
                meetingId, meetingDateStartFrom, meetingDateEndTo
        );
        if (!rangeVerifyResult) {
            throw new DateRangeOutOfMeetingCalendarException("모임일 확정 일자가 모임 캘린더 범위에 포함되지 않습니다. meetingId: " + meetingId +
                    ", startFrom: " + meetingDateStartFrom.format(DateTimeFormatter.ISO_DATE) +
                    ", endTo: " + meetingDateEndTo.format(DateTimeFormatter.ISO_DATE));
        }
    }

    private void generateFixedDateExistError(FixedDate fixedDate) {
        throw new FixedDateAlreadyExistException(fixedDate);
    }
}
