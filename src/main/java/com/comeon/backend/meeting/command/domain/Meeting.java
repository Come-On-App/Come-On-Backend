package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.event.Events;
import com.comeon.backend.common.model.BaseTimeEntity;
import com.comeon.backend.meeting.command.domain.event.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Embedded
    private MeetingMetaData metaData;

    @Embedded
    private EntryCode entryCode;

    @Embedded
    private MeetingTime meetingTime;

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    public MeetingDate meetingDate;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingPlace> meetingPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DateVoting> dateVotingList = new ArrayList<>();

    public Meeting(MeetingInfo meetingInfo, Long hostUserId) {
        this.metaData = new MeetingMetaData(meetingInfo);
        this.entryCode = EntryCode.create();
        this.meetingTime = MeetingTime.init();
        this.members.add(Member.createHost(this, hostUserId));
    }

    public Long getId() {
        return id;
    }

    public void modifyMeetingInfo(MeetingInfo meetingInfo) {
        this.metaData.modify(meetingInfo);
        this.dateVotingList.removeIf(voting -> !this.metaData.verifyDateInMeetingCalendar(voting.getDate()));

        cancelMeetingDate();

        Events.raise(MeetingMetaDataUpdateEvent.create(this.id));
    }

    public EntryCode renewEntryCode() {
        this.entryCode = EntryCode.create();
        return this.entryCode;
    }

    public void modifyMeetingTime(LocalTime meetingStartTime) {
        // TODO Event
        this.meetingTime = MeetingTime.create(meetingStartTime);
    }

    public Member join(Long userId) {
        this.members.stream()
                .filter(member -> member.getUserId().equals(userId))
                .findAny()
                .ifPresent(member -> {
                    throw new MemberAlreadyJoinedException(member);
                });

        Member participant = Member.createParticipant(this, userId);
        this.members.add(participant);

        raiseMemberListUpdateEvent();

        return participant;
    }

    private void raiseMemberListUpdateEvent() {
        Events.raise(MemberListUpdateEvent.create(this.id));
    }

    public void changeHost(Long targetUserId) {
        this.members.stream()
                .filter(Member::isHost)
                .findFirst()
                .ifPresent(Member::updateRoleToParticipant);

        this.members.stream()
                .filter(member -> member.getUserId().equals(targetUserId))
                .findFirst()
                .ifPresentOrElse(Member::updateRoleToHost, () -> {
                    throw new MemberNotExistException();
                });

        raiseMemberListUpdateEvent();
    }

    public void leave(Long userId) {
        Member memberToLeave = this.members.stream()
                .filter(member -> member.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(MemberNotExistException::new);
        this.members.remove(memberToLeave);

        if (memberToLeave.isHost()) {
            members.stream()
                    .min(Comparator.comparing(BaseTimeEntity::getCreatedDate))
                    .ifPresentOrElse(Member::updateRoleToHost, () -> Events.raise(AllMemberLeftEvent.create(this.id)));
        }

        raiseMemberListUpdateEvent();
    }

    public void drop(Long userId) {
        Member memberToDrop = this.members.stream()
                .filter(member -> member.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(MemberNotExistException::new);

        if (memberToDrop.isHost()) {
            throw new HostCannotDropException();
        }

        this.members.remove(memberToDrop);

        raiseMemberListUpdateEvent();
    }

    public MeetingPlace addPlace(PlaceInfo placeInfo) {
        placeInfo.setOrder(this.meetingPlaces.size() + 1);
        MeetingPlace meetingPlace = new MeetingPlace(this, placeInfo);
        this.meetingPlaces.add(meetingPlace);

        raiseMeetingPlaceListUpdateEvent();

        return meetingPlace;
    }

    private void raiseMeetingPlaceListUpdateEvent() {
        Events.raise(MeetingPlaceListUpdateEvent.create(this.id));
    }

    public void modifyPlace(Long meetingPlaceId, PlaceInfo placeInfo) {
        this.meetingPlaces.stream()
                .filter(meetingPlace -> meetingPlace.getId().equals(meetingPlaceId))
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(meetingPlaceId))
                .update(placeInfo);

        raiseMeetingPlaceListUpdateEvent();
    }

    public void removePlace(Long meetingPlaceId) {
        MeetingPlace mp = this.meetingPlaces.stream()
                .filter(meetingPlace -> meetingPlace.getId().equals(meetingPlaceId))
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(meetingPlaceId));
        this.meetingPlaces.remove(mp);

        this.meetingPlaces.stream()
                .filter(meetingPlace -> meetingPlace.getOrder() > mp.getOrder())
                .forEach(MeetingPlace::decreaseOrder);

        raiseMeetingPlaceListUpdateEvent();
    }

    public void confirmMeetingDate(LocalDate startFrom, LocalDate endTo) {
        if (!metaData.verifyDateRangeInMeetingCalendar(startFrom, endTo)) {
            throw new DateRangeOutOfMeetingCalendarException(this.id, startFrom, endTo);
        }

        if (this.meetingDate != null) {
            meetingDate.update(startFrom, endTo);
            return;
        }

        this.meetingDate = new MeetingDate(this, startFrom, endTo);

        raiseMeetingDateUpdateEvent();
    }

    private void raiseMeetingDateUpdateEvent() {
        Events.raise(MeetingDateUpdateEvent.create(this.id));
    }

    public void cancelMeetingDate() {
        if (this.meetingDate == null) {
            throw new MeetingDateNotExistException();
        }

        this.meetingDate = null;

        raiseMeetingDateUpdateEvent();
    }

    public void addVoting(Long userId, LocalDate date) {
        if (!this.metaData.verifyDateInMeetingCalendar(date)) {
            throw new DateOutOfMeetingCalendarException();
        }

        this.dateVotingList.stream()
                .filter(voting -> voting.getUserId().equals(userId) && voting.getDate().equals(date))
                .findFirst()
                .ifPresent(voting -> {
                    throw new DateVotingAlreadyExistException();
                });

        this.dateVotingList.add(new DateVoting(this, userId, date));

        raiseDateVotingListUpdateEvent(date);
    }

    private void raiseDateVotingListUpdateEvent(LocalDate date) {
        Events.raise(DateVotingListUpdateEvent.create(this.id, date));
    }

    public void removeVoting(Long userId, LocalDate date) {
        if (!this.metaData.verifyDateInMeetingCalendar(date)) {
            throw new DateOutOfMeetingCalendarException();
        }

        DateVoting votingToRemove = this.dateVotingList.stream()
                .filter(voting -> voting.getUserId().equals(userId) && voting.getDate().equals(date))
                .findFirst()
                .orElseThrow(DateVotingNotExistException::new);
        this.dateVotingList.remove(votingToRemove);

        raiseDateVotingListUpdateEvent(date);
    }

    public void lockPlace(Long userId, Long meetingPlaceId) {
        MeetingPlace mp = this.meetingPlaces.stream()
                .filter(meetingPlace -> meetingPlace.getId().equals(meetingPlaceId))
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(meetingPlaceId));
        mp.lock(userId);

        raisePlaceLockEvent(userId, meetingPlaceId);
    }

    private void raisePlaceLockEvent(Long userId, Long meetingPlaceId) {
        Events.raise(MeetingPlaceLockEvent.create(this.id, meetingPlaceId, userId));
    }

    public void unlockPlace(Long userId, Long meetingPlaceId) {
        MeetingPlace mp = this.meetingPlaces.stream()
                .filter(meetingPlace -> meetingPlace.getId().equals(meetingPlaceId))
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(meetingPlaceId));
        mp.unlock(userId);

        raisePlaceUnlockEvent(userId, meetingPlaceId);
    }

    private void raisePlaceUnlockEvent(Long userId, Long meetingPlaceId) {
        Events.raise(MeetingPlaceUnlockEvent.create(this.id, meetingPlaceId, userId));
    }

    public void modifyPlaceWithUnlock(Long userId, Long meetingPlaceId, PlaceInfo placeInfo) {
        MeetingPlace mp = this.meetingPlaces.stream()
                .filter(meetingPlace -> meetingPlace.getId().equals(meetingPlaceId))
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(meetingPlaceId));
        mp.updateWithUnlock(userId, placeInfo);

        raiseMeetingPlaceListUpdateEvent();
        raisePlaceUnlockEvent(userId, meetingPlaceId);
    }
}
