package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.query.dto.EntryCodeDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingCalendarResponse;
import com.comeon.backend.meeting.query.dto.MeetingDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingSliceResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {

    List<MeetingSliceResponse> selectMeetingSlice(MeetingSliceParam param);
    EntryCodeDetailsResponse selectEntryCodeDetails(Long meetingId);
    MeetingDetailsResponse selectMeetingDetails(Long meetingId);
    MeetingCalendarResponse selectMeetingCalendar(Long meetingId);
}
