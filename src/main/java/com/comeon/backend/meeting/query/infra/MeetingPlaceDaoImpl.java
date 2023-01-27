package com.comeon.backend.meeting.query.infra;

import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dao.dto.PlaceListResponse;
import com.comeon.backend.meeting.query.infra.mapper.MeetingPlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingPlaceDaoImpl implements MeetingPlaceDao {

    private final MeetingPlaceMapper placeMapper;

    @Override
    public List<PlaceListResponse> findPlacesByMeetingId(Long meetingId) {
        return placeMapper.selectPlacesByMeetingId(meetingId);
    }
}
