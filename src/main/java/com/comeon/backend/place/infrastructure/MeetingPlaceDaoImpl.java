package com.comeon.backend.place.infrastructure;

import com.comeon.backend.place.query.MeetingPlaceDao;
import com.comeon.backend.place.query.PlaceDetails;
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
