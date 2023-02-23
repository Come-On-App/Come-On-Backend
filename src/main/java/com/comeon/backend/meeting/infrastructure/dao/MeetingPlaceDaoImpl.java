package com.comeon.backend.meeting.infrastructure.dao;

import com.comeon.backend.meeting.infrastructure.mapper.MeetingPlaceMapper;
import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dto.PlaceDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingPlaceDaoImpl implements MeetingPlaceDao {

    private final MeetingPlaceMapper placeMapper;

    @Override
    public List<PlaceDetails> findPlacesByMeetingId(Long meetingId) {
        return placeMapper.selectPlacesByMeetingId(meetingId);
    }
}
