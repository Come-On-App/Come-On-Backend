package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMetaData {

    private String name;
    private String thumbnailImageUrl;

    @Embedded
    private Calendar calendar;

    public MeetingMetaData(MeetingInfo meetingInfo) {
        this.name = meetingInfo.getMeetingName();
        this.thumbnailImageUrl = meetingInfo.getMeetingImageUrl();
        this.calendar = Calendar.create(meetingInfo.getCalendarStartFrom(), meetingInfo.getCalendarEndTo());
    }

    public void modify(MeetingInfo meetingInfo) {
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
        }
    }

    public boolean verifyDateInMeetingCalendar(LocalDate date) {
        return this.calendar.verifyDate(date);
    }

    public boolean verifyDateRangeInMeetingCalendar(LocalDate startFrom, LocalDate endTo) {
        return this.calendar.verifyDateRange(startFrom, endTo);
    }
}
