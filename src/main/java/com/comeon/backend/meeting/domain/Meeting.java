package com.comeon.backend.meeting.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    private String name;
    private String thumbnailImageUrl;

    private LocalDate calendarStartFrom;
    private LocalDate calendarEndTo;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingMember> members = new ArrayList<>();

    @Builder
    public Meeting(Long hostUserId, String name, String thumbnailImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.calendarStartFrom = calendarStartFrom;
        this.calendarEndTo = calendarEndTo;

        members.add(MeetingMember.createHostMember(hostUserId, this));
    }
}
