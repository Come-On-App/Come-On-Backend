package com.comeon.backend.meeting.command.domain;

import java.util.Optional;

public interface MeetingRepository {

    Meeting save(Meeting meeting);
    Optional<Meeting> findMeetingBy(Long meetingId);
}
