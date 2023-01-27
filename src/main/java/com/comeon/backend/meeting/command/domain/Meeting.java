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
    private MeetingEntryCode entryCode;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingPlace> places = new ArrayList<>();

    @Builder
    public Meeting(Long hostUserId, String name, String thumbnailImageUrl,
                   LocalDate calendarStartFrom, LocalDate calendarEndTo) {
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.calendarStartFrom = calendarStartFrom;
        this.calendarEndTo = calendarEndTo;

        members.add(MeetingMember.createHostMember(hostUserId, this));
    }

    public MeetingMember join(Long participantUserId) {
        MeetingMember participantMember = MeetingMember.createParticipantMember(participantUserId, this);
        members.add(participantMember);
        return participantMember;
    }

    public MeetingPlace createPlace(Long userId, PlaceInfo placeInfo) {
        MeetingPlace place = MeetingPlace.builder()
                .meeting(this)
                .userId(userId)
                .placeInfo(placeInfo)
                .placeOrder(this.places.size() + 1)
                .build();
        places.add(place);

        return place;
    }

    public void modifyPlaceByPlaceId(Long placeId, Long userId, PlaceInfo placeInfo) {
        MeetingPlace mp = getPlaceByPlaceId(placeId);
        mp.update(userId, placeInfo);
    }

    private MeetingPlace getPlaceByPlaceId(Long placeId) {
        return this.places.stream()
                .filter(place -> place.getId() == placeId)
                .findFirst()
                .orElseThrow(() -> new PlaceNotExistException(placeId));
    }

    public void removePlaceByPlaceId(Long placeId) {
        MeetingPlace mp = getPlaceByPlaceId(placeId);
        this.places.remove(mp);
        arrangeOrder(mp.getOrder());
    }

    private void arrangeOrder(int startOrder) {
        this.places.stream()
                .filter(place -> place.getOrder() > startOrder)
                .forEach(MeetingPlace::decreaseOrder);
    }

    public MeetingEntryCode renewEntryCodeAndGet() {
        if (this.entryCode == null) {
            this.entryCode = MeetingEntryCode.createWithRandomCode(this);
        } else {
            entryCode.renewCode();
        }

        return this.entryCode;
    }
}
