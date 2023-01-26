package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dao.dto.PlaceListResponse;

import java.util.List;

public interface MeetingPlaceDao {

    List<PlaceListResponse> findPlacesByMeetingId(Long meetingId);
}
