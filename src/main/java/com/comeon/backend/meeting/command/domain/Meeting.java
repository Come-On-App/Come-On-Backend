package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
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
public class Meeting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    private String name;
    private String thumbnailImageUrl;

    private LocalDate calendarStartFrom;
    private LocalDate calendarEndTo;

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntryCode entryCode;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Builder
    public Meeting(Long hostUserId, String name, String thumbnailImageUrl,
                   LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.calendarStartFrom = calendarStartFrom;
        this.calendarEndTo = calendarEndTo;

        members.add(Member.createHostMember(this, hostUserId));
    }

    public Member join(Long userId) {
        checkExistMember(userId);
        Member participant = Member.createParticipantMember(this, userId);
        members.add(participant);
        return participant;
    }

    private void checkExistMember(Long userId) {
        members.stream()
                .filter(member -> member.getUserId().equals(userId))
                .findFirst()
                .ifPresent(this::generateJoinedError);
    }

    private void generateJoinedError(Member member) {
        throw new MemberAlreadyJoinedException(member);
    }

    public EntryCode renewEntryCodeAndGet() {
        if (this.entryCode == null) {
            this.entryCode = EntryCode.createWithRandomCode(this);
        } else {
            entryCode.renewCode();
        }

        return this.entryCode;
    }
}
