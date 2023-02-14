package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.event.Events;
import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    private String name;
    private String thumbnailImageUrl;

    @Embedded
    private Calendar calendar;

    @Embedded
    private EntryCode entryCode;

    @Embedded
    private MeetingTime meetingTime;

    @Transient
    private Long createdUserId;

    @Builder
    public Meeting(String name, String thumbnailImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo, Long createdUserId) {
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.calendar = Calendar.create(calendarStartFrom, calendarEndTo);
        this.entryCode = EntryCode.create();
        this.meetingTime = new MeetingTime();

        this.createdUserId = createdUserId;
    }

    public void modifyMeetingInfo(MeetingInfo meetingInfo) {
        if (meetingInfo.getMeetingName() != null) {
            this.name = meetingInfo.getMeetingName();
        }

        if (meetingInfo.getMeetingImageUrl() != null) {
            this.thumbnailImageUrl = meetingInfo.getMeetingImageUrl();
        }

        if (meetingInfo.getCalendarStartFrom() != null || meetingInfo.getCalendarEndTo() != null) {
            LocalDate startFromToModify = meetingInfo.getCalendarStartFrom() != null
                    ? meetingInfo.getCalendarStartFrom()
                    : this.calendar.getStartFrom();
            LocalDate endToToModify = meetingInfo.getCalendarEndTo() != null
                    ? meetingInfo.getCalendarEndTo()
                    : this.calendar.getEndTo();

            this.calendar = Calendar.create(startFromToModify, endToToModify);

            Events.raise(MeetingCalendarModifyEvent.create(this.id, this.calendar.getStartFrom(), this.calendar.getEndTo()));
        }

        Events.raise(MeetingInfoModifyEvent.create(this.id));
    }

    public void renewEntryCode() {
        this.entryCode = EntryCode.create();
    }

    public void modifyMeetingTime(LocalTime meetingTime) {
        // TODO 모임 시간 변경 이벤트 발생
        this.meetingTime = new MeetingTime(meetingTime);
    }

    @PostPersist
    public void postPersist() {
        MeetingCreateEvent createEvent = MeetingCreateEvent.create(this.id, this.createdUserId);
        Events.raise(createEvent);
    }
}
