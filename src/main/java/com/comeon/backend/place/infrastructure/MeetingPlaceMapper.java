package com.comeon.backend.place.infrastructure;

import com.comeon.backend.place.query.dto.PlaceListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingPlaceMapper {

    List<PlaceListResponse> selectPlacesByMeetingId(Long meetingId);
}
