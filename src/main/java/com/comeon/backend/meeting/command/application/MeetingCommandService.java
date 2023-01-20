package com.comeon.backend.meeting.command.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.meeting.command.domain.*;
import com.comeon.backend.meeting.common.MeetingErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingCommandService {

    private final MeetingRepository meetingRepository;

    public Long addMeeting(Long userId, String meetingName, String meetingImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        Meeting meeting = Meeting.builder()
                .hostUserId(userId)
                .name(meetingName)
                .thumbnailImageUrl(meetingImageUrl)
                .calendarStartFrom(calendarStartFrom)
                .calendarEndTo(calendarEndTo)
                .build();

        return meetingRepository.saveMeeting(meeting).getId();
    }

    public String renewEntryCode(Long meetingId, Long userId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_EXIST));
        checkHostUser(meetingId, userId);
        MeetingEntryCode entryCode = renewCode(meeting);
        return entryCode.getCode();
    }

    private MeetingEntryCode renewCode(Meeting meeting) {
        MeetingEntryCode entryCode = meetingRepository.findEntryCodeBy(meeting.getId())
                .orElse(MeetingEntryCode.createWithRandomCode(meeting));
        if (entryCode.getId() == null) {
            meetingRepository.saveEntryCode(entryCode);
        } else {
            entryCode.renewCode();
        }
        return entryCode;
    }

    private void checkHostUser(Long meetingId, Long userId) {
        meetingRepository.findMeetingMemberBy(meetingId, userId)
                .filter(meetingMember -> meetingMember.getRole() == MeetingMemberRole.HOST)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.NO_AUTHORITIES));
    }
}
