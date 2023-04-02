package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MeetingDao {

    Long findMeetingIdByEntryCode(String entryCode);

    Slice<MeetingSimple> findMeetingSlice(Long userId, Pageable pageable, MeetingSliceCondition cond);
    EntryCodeDetails findEntryCodeDetails(Long meetingId);
    MeetingDetails findMeetingDetails(Long meetingId, Long userId);
    MeetingCalendarDetails findMeetingCalendar(Long meetingId);
    MeetingTimeSimple findMeetingTimeSimple(Long meetingId);
    MeetingDetailForReport findMeetingDetailForReport(Long meetingId);
}
