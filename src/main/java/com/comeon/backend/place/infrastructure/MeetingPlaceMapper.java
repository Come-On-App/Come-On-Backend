package com.comeon.backend.place.infrastructure;

import com.comeon.backend.place.query.PlaceDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingPlaceMapper {

    List<PlaceDetails> selectPlacesByMeetingId(Long meetingId);
}
