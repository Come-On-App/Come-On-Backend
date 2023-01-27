package com.comeon.backend.meeting.command.domain;

import java.util.Optional;

public interface MeetingRepository {

    Meeting saveMeeting(Meeting meeting);
    Optional<Meeting> findMeetingBy(Long meetingId);
    Optional<Meeting> findMeetingBy(String entryCode);
    Optional<MeetingMember> findMeetingMemberBy(Long meetingId, Long userId);
    Optional<Meeting> findMeetingFetchPlacesBy(Long meetingId);
    void flush();
}
