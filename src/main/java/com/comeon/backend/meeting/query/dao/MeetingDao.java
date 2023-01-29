package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.EntryCodeDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingCalendarResponse;
import com.comeon.backend.meeting.query.dto.MeetingDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingSliceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MeetingDao {

    Slice<MeetingSliceResponse> findMeetingSlice(Long userId, Pageable pageable, MeetingSliceCondition cond);
    EntryCodeDetailsResponse findEntryCodeDetails(Long meetingId);
    MeetingDetailsResponse findMeetingDetails(Long meetingId);
    MeetingCalendarResponse findMeetingCalendar(Long meetingId);
}
