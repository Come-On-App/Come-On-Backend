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

    public EntryCodeDetails renewEntryCode(Long meetingId, Long userId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_EXIST));
        checkHostUser(meetingId, userId);
        MeetingEntryCode entryCode = renewCode(meeting);
        return new EntryCodeDetails(meetingId, entryCode.getCode(), entryCode.getLastModifiedDate().plusDays(7));
    }

    private void checkHostUser(Long meetingId, Long userId) {
        meetingRepository.findMeetingMemberBy(meetingId, userId)
                .ifPresentOrElse(
                        meetingMember -> {
                            // 모임에 가입된 유저이면 권한 확인
                            if (meetingMember.getRole() != MeetingMemberRole.HOST) {
                                throw new RestApiException(MeetingErrorCode.NO_AUTHORITIES);
                            }
                        },
                        // 그렇지 않으면 예외 발생
                        () -> {
                            throw new RestApiException(MeetingErrorCode.NOT_MEMBER);
                        }
                );
    }

    private MeetingEntryCode renewCode(Meeting meeting) {
        MeetingEntryCode entryCode = meetingRepository.findEntryCodeBy(meeting.getId())
                .orElse(MeetingEntryCode.createWithRandomCode(meeting));
        if (entryCode.getId() != null) {
            entryCode.renewCode();
        }
        return meetingRepository.saveEntryCodeAndFlush(entryCode);
    }
}
