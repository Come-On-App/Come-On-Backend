package com.comeon.backend.place.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceCategory {

    SHOPPING("쇼핑"),
    ATTRACTION("관광명소"),
    CULTURE("문화시설"),
    ACTIVITY("액티비티"),
    ACCOMMODATION("숙박"),
    RESTAURANT("음식점"),
    BAR("술집"),
    CAFE("카페"),
    SPORT("스포츠"),
    SCHOOL("학교"),
    ETC("기타");

    private final String description;

    public static PlaceCategory of(String name) {
        for (PlaceCategory placeCategory : PlaceCategory.values()) {
            if (placeCategory.name().equalsIgnoreCase(name)) {
                return placeCategory;
            }
        }
        return null;
    }
}
