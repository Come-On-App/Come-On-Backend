package com.comeon.backend.meeting.query.infra.mapper;

import com.comeon.backend.meeting.query.dao.dto.PlaceListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingPlaceMapper {

    List<PlaceListResponse> selectPlacesByMeetingId(Long meetingId);
}
