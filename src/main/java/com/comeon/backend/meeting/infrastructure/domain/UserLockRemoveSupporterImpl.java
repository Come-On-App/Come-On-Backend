package com.comeon.backend.meeting.infrastructure.domain;

import com.comeon.backend.meeting.command.domain.MeetingPlace;
import com.comeon.backend.meeting.command.domain.UserLockRemoveSupporter;
import com.comeon.backend.meeting.infrastructure.domain.repository.MeetingPlaceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLockRemoveSupporterImpl implements UserLockRemoveSupporter {

    private final MeetingPlaceJpaRepository meetingPlaceJpaRepository;

    @Override
    public void removeAllLockByUserId(Long userId) {
        List<MeetingPlace> lockedMeetingPlaces = meetingPlaceJpaRepository.findLockedMeetingPlaces(userId);
        lockedMeetingPlaces.forEach(mp -> mp.getMeeting().unlockPlace(userId, mp.getId()));
    }
}
