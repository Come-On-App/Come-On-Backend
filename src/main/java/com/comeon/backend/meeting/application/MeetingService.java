package com.comeon.backend.meeting.application;

import com.comeon.backend.meeting.domain.Meeting;
import com.comeon.backend.meeting.domain.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public Long addMeeting(Long userId, String meetingName, String meetingImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        Meeting meeting = Meeting.builder()
                .hostUserId(userId)
                .name(meetingName)
                .thumbnailImageUrl(meetingImageUrl)
                .calendarStartFrom(calendarStartFrom)
                .calendarEndTo(calendarEndTo)
                .build();

        return meetingRepository.save(meeting).getId();
    }
}
