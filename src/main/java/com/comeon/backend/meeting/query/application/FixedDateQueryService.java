package com.comeon.backend.meeting.query.application;

import com.comeon.backend.meeting.query.dao.FixedDateDao;
import com.comeon.backend.meeting.query.dto.MeetingFixedDateSummary;
import com.comeon.backend.meeting.query.dto.MeetingFixedDatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FixedDateQueryService {

    private final FixedDateDao fixedDateDao;

    public MeetingFixedDatesResponse findMeetingFixedDates(Long userId, int baseYear, int baseMonth) {
        LocalDate baseCalendar = LocalDate.of(baseYear, baseMonth, 1);
        LocalDate startFrom = baseCalendar.minusMonths(6);
        LocalDate endTo = baseCalendar.plusMonths(6).minusDays(1);

        List<MeetingFixedDateSummary> meetingFixedDatesSummary =
                fixedDateDao.findMeetingFixedDatesSummary(userId, startFrom, endTo);

        return new MeetingFixedDatesResponse(
                meetingFixedDatesSummary.stream()
                        .collect(Collectors.groupingBy(
                                summary -> summary.getFixedDate().getStartDate().getYear(),
                                Collectors.groupingBy(
                                        summary -> summary.getFixedDate().getStartDate().getMonthValue()
                                )
                        ))
                        .entrySet().stream()
                        .map(mapEntry -> new MeetingFixedDatesResponse.YearMeetings(
                                mapEntry.getKey(),
                                mapEntry.getValue().entrySet().stream()
                                        .map(entry -> new MeetingFixedDatesResponse.MonthCalendar(entry.getKey(), entry.getValue()))
                                        .collect(Collectors.toList())
                        )).collect(Collectors.toList())
        );
    }
}
