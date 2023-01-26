package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlace extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_place_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

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
    public MeetingPlace(Meeting meeting, Long userId, PlaceInfo placeInfo, int placeOrder) {
        this.meeting = meeting;
        this.name = placeInfo.getPlaceName();
        this.memo = placeInfo.getPlaceMemo();
        this.address = placeInfo.getAddress();
        this.lng = placeInfo.getLng();
        this.lat = placeInfo.getLat();
        this.category = placeInfo.getCategory();
        this.googlePlaceId = placeInfo.getGooglePlaceId();
        this.order = placeOrder;
        this.lastModifiedUserId = userId;
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
