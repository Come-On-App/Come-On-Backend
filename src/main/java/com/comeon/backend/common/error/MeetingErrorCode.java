package com.comeon.backend.common.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MeetingErrorCode implements ErrorCode {
    ALREADY_JOINED(3000, HttpStatus.BAD_REQUEST, "이미 해당 모임에 가입하셨습니다."),
    ENTRY_CODE_NOT_MATCHED(3001, HttpStatus.BAD_REQUEST, "입력한 입장코드와 일치하는 모임이 없습니다."),
    MEETING_NOT_EXIST(3002, HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다."),
    NOT_MEMBER(3003, HttpStatus.FORBIDDEN, "가입되지 않은 모임입니다."),
    NO_AUTHORITIES(3004, HttpStatus.FORBIDDEN, "해당 요청을 처리할 권한(모임 권한)이 부족합니다."),

    INVALID_PLACE_ID(3100, HttpStatus.NOT_FOUND, "존재하지 않는 장소 식별값입니다."),

    DATE_VOTING_EXIST(3200, HttpStatus.BAD_REQUEST, "이미 해당 일자에 투표하셨습니다."),
    NO_DATE_VOTING_EXIST(3201, HttpStatus.BAD_REQUEST, "해당 일자에 투표한 이력이 없습니다."),
    DATE_OUT_OF_CALENDAR_RANGE(3202, HttpStatus.BAD_REQUEST, "투표 가능 범위에 해당하지 않는 일자입니다.")
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
