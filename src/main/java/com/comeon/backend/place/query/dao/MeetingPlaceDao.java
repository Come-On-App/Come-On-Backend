package com.comeon.backend.place.query.dao;

import com.comeon.backend.place.query.dto.PlaceListResponse;

import java.util.List;

public interface MeetingPlaceDao {

    List<PlaceListResponse> findPlacesByMeetingId(Long meetingId);
}
