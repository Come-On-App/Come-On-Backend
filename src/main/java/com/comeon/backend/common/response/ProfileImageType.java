package com.comeon.backend.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileImageType {

    DEFAULT("유저 커스텀 프로필 이미지 없음. 기본 프로필 이미지."),
    CUSTOM("유저가 설정한 커스텀 프로필 이미지"),
    ;

    private final String description;
}
