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
    public MeetingPlace(Meeting meeting, String name, String memo,
                        String address, Double lng, Double lat, PlaceCategory category,
                        int order, String googlePlaceId, Long userId) {
        this.meeting = meeting;
        this.name = name;
        this.memo = memo;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.category = category;
        this.order = order;
        this.googlePlaceId = googlePlaceId;
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
        if (name != null) {
            this.name = name;
        }
    }

    private void updateMemo(String memo) {
        this.memo = memo;
    }

    private void updateAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    private void updateLat(Double lat) {
        if (lat != null) {
            this.lat = lat;
        }
    }

    private void updateLng(Double lng) {
        if (lng != null) {
            this.lng = lng;
        }
    }

    private void updateCategory(PlaceCategory category) {
        if (category != null) {
            this.category = category;
        }
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
