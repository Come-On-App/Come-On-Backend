package com.comeon.backend.meeting.infrastructure.mapper;

import com.comeon.backend.meeting.query.dto.LockedPlaceSimple;
import com.comeon.backend.meeting.query.dto.PlaceDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingPlaceMapper {

    List<PlaceDetails> selectPlacesByMeetingId(Long meetingId);

    List<LockedPlaceSimple> selectLockedPlacesByMeetingId(Long meetingId);
}
