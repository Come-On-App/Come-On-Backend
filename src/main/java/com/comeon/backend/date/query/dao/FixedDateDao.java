package com.comeon.backend.date.query.dao;

import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;
import com.comeon.backend.date.query.dto.MeetingFixedDateSummary;

import java.time.LocalDate;
import java.util.List;

public interface FixedDateDao {

    MeetingFixedDateSimple findFixedDateSimple(Long meetingId);
    List<MeetingFixedDateSummary> findMeetingFixedDatesSummary(Long userId, LocalDate searchStartFrom, LocalDate searchEndTo);
}
