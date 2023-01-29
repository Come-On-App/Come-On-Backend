package com.comeon.backend.place.infrastructure;

import com.comeon.backend.place.command.domain.MeetingPlace;
import com.comeon.backend.place.command.domain.MeetingPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeetingPlaceRepositoryImpl implements MeetingPlaceRepository {

    private final MeetingPlaceJpaRepository meetingPlaceJpaRepository;

    @Override
    public MeetingPlace save(MeetingPlace meetingPlace) {
        return meetingPlaceJpaRepository.save(meetingPlace);
    }

    @Override
    public List<MeetingPlace> findByMeetingId(Long meetingId) {
        return meetingPlaceJpaRepository.findByMeetingId(meetingId);
    }

    @Override
    public Optional<MeetingPlace> findByMeetingIdAndPlaceId(Long meetingId, Long placeId) {
        return meetingPlaceJpaRepository.findByIdAndMeetingId(placeId, meetingId);
    }
}
