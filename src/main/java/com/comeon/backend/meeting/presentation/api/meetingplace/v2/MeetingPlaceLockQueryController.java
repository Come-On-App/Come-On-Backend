package com.comeon.backend.meeting.presentation.api.meetingplace.v2;

import com.comeon.backend.meeting.query.dao.MeetingPlaceDao;
import com.comeon.backend.meeting.query.dto.LockedPlaceSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/meetings/{meetingId}/places/lock")
public class MeetingPlaceLockQueryController {

    private final MeetingPlaceDao meetingPlaceDao;

    // TODO Admin
    @GetMapping
    public LockedMeetingPlaceListResponse lockedMeetingPlaceList(
            @PathVariable Long meetingId
    ) {
        List<LockedPlaceSimple> result = meetingPlaceDao.findLockedPlaceByMeetingId(meetingId);
        return new LockedMeetingPlaceListResponse(result);
    }
}
