package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.MeetingFixedDateSimple;
import com.comeon.backend.meeting.query.dto.MeetingFixedDateSummary;

import java.time.LocalDate;
import java.util.List;

public interface FixedDateDao {

    MeetingFixedDateSimple findFixedDateSimple(Long meetingId);
    List<MeetingFixedDateSummary> findMeetingFixedDatesSummary(Long userId, LocalDate searchStartFrom, LocalDate searchEndTo);
}
