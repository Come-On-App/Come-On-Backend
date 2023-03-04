package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import com.comeon.backend.meeting.query.dto.LockedPlaceSimple;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LockedMeetingPlaceListResponse {

    private List<LockedPlaceSimple> lockedPlaces;
}
