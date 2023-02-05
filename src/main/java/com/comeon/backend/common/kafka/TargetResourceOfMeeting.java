package com.comeon.backend.common.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetResourceOfMeeting {

    PLACES("모임 장소 리스트"),
    META_DATA("모임 메타 데이터"),
    MEMBERS("모임 가입 회원 리스트"),
    VOTING("모임일 투표 리스트"),
    FIXED_DATE("확정된 모임일"),
    ;

    private final String description;
}
