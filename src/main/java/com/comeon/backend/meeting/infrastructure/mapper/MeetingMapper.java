package com.comeon.backend.meeting.infrastructure.mapper;

import com.comeon.backend.meeting.query.dto.EntryCodeDetails;
import com.comeon.backend.meeting.query.dto.MeetingCalendarDetails;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import com.comeon.backend.meeting.query.dto.MeetingSimple;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {

    List<MeetingSimple> selectMeetingSlice(MeetingSliceParam param);
    EntryCodeDetails selectEntryCodeDetails(Long meetingId);
    MeetingDetails selectMeetingDetails(Long meetingId, Long userId);
    MeetingCalendarDetails selectMeetingCalendar(Long meetingId);
    Long selectMeetingId(String entryCode);
}
