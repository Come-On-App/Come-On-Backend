package com.comeon.backend.meeting.query.infra.mapper;

import com.comeon.backend.meeting.query.dao.dto.EntryCodeDetailsResponse;
import com.comeon.backend.meeting.query.dao.dto.MeetingDetailsResponse;
import com.comeon.backend.meeting.query.dao.dto.MeetingSliceResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {

    List<MeetingSliceResponse> selectMeetingSlice(MeetingSliceParam param);
    EntryCodeDetailsResponse selectEntryCodeDetails(Long meetingId);
    MeetingDetailsResponse selectMeetingDetails(Long meetingId);
}
