package com.comeon.backend.meeting.infrastructure.mapper;

import com.comeon.backend.meeting.query.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {

    List<MeetingSimple> selectMeetingSlice(MeetingSliceParam param);
    EntryCodeDetails selectEntryCodeDetails(Long meetingId);
    MeetingDetails selectMeetingDetails(Long meetingId, Long userId);
    MeetingCalendarDetails selectMeetingCalendar(Long meetingId);
    Long selectMeetingId(String entryCode);
    MeetingTimeSimple selectMeetingTime(Long meetingId);

    MeetingDetailForReport selectMeetingDetailForReport(Long meetingId);
}
