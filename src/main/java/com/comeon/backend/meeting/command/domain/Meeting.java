package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.event.Events;
import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    public Meeting(String name, String thumbnailImageUrl, Calendar calendar, Long createdUserId) {
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.calendar = calendar;
        this.entryCode = EntryCode.create();
        this.meetingTime = new MeetingTime();

        this.createdUserId = createdUserId;
    }

    public void renewEntryCode() {
        this.entryCode = EntryCode.create();
    }

    @PostPersist
    public void postPersist() {
        MeetingCreateEvent createEvent = MeetingCreateEvent.create(this.id, this.createdUserId);
        Events.raise(createEvent);
    }

    public void modifyMeetingTime(LocalTime meetingTime) {
        this.meetingTime = new MeetingTime(meetingTime);
    }
}
