package com.comeon.backend.place.command.domain;

import com.comeon.backend.common.domain.BaseTimeEntity;
import com.comeon.backend.common.event.Events;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@Table(name = "meeting_place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlace extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_place_id")
    private Long id;

    private Long meetingId;

    private String name;

    private String memo;

    private String address;

    private Double lng;

    private Double lat;

    @Enumerated(EnumType.STRING)
    private PlaceCategory category;

    @Column(name = "place_order")
    private int order;

    private String googlePlaceId;

    private Long lastModifiedUserId;

    @Builder
    public MeetingPlace(Long meetingId, Long userId, PlaceInfo placeInfo, int placeOrder) {
        this.meetingId = meetingId;
        this.name = placeInfo.getPlaceName();
        this.memo = placeInfo.getPlaceMemo();
        this.address = placeInfo.getAddress();
        this.lng = placeInfo.getLng();
        this.lat = placeInfo.getLat();
        this.category = placeInfo.getCategory();
        this.googlePlaceId = placeInfo.getGooglePlaceId();
        this.order = placeOrder;
        this.lastModifiedUserId = userId;

        Events.raise(PlacesUpdateEvent.create(meetingId));
    }

    public void decreaseOrder() {
        --this.order;
    }

    public void update(Long userId, PlaceInfo placeInfo) {
        updateName(placeInfo.getPlaceName());
        updateMemo(placeInfo.getPlaceMemo());
        updateAddress(placeInfo.getAddress());
        updateLat(placeInfo.getLat());
        updateLng(placeInfo.getLng());
        updateCategory(placeInfo.getCategory());
        updateGooglePlaceId(placeInfo.getGooglePlaceId());

        updateLastModifiedUserId(userId);

        Events.raise(PlacesUpdateEvent.create(meetingId));
    }

    private void updateName(String name) {
        this.name = name;
    }

    private void updateMemo(String memo) {
        this.memo = memo;
    }

    private void updateAddress(String address) {
        this.address = address;
    }

    private void updateLat(Double lat) {
        this.lat = lat;
    }

    private void updateLng(Double lng) {
        this.lng = lng;
    }

    private void updateCategory(PlaceCategory category) {
        this.category = category;
    }

    private void updateGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    private void updateLastModifiedUserId(Long userId) {
        if (userId != null) {
            this.lastModifiedUserId = userId;
        }
    }
}
