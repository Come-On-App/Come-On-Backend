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
    HOST_ALREADY_EXIST(3005, HttpStatus.BAD_REQUEST, "이미 HOST 권한을 가진 멤버가 존재합니다. HOST 권한을 가진 멤버는 둘 이상 존재할 수 없습니다."),
    MEETING_CALENDAR_RANGE_INVALID(3006, HttpStatus.BAD_REQUEST, "지정한 캘린더 범위가 유효하지 않습니다. 캘린더의 종료일은 시작일과 같거나 시작일 이후여야 합니다."),
    MEMBER_NOT_EXIST(3007, HttpStatus.BAD_REQUEST, "해당 모임에 가입되지 않은 유저입니다."),
    HOST_CANNOT_DROP(3008, HttpStatus.BAD_REQUEST, "방장은 강퇴할 수 없습니다."),

    INVALID_PLACE_ID(3100, HttpStatus.NOT_FOUND, "존재하지 않는 장소 식별값입니다."),
    PLACE_LOCK_ALREADY_EXIST(3101, HttpStatus.LOCKED, "다른 사용자가 장소를 수정중입니다. 잠시 후 다시 요청해주세요."),
    PLACE_LOCK_NOT_EXIST(3102, HttpStatus.BAD_REQUEST, "해당 장소에 락이 존재하지 않습니다. 장소에 락을 먼저 수행한 후 요청해주세요."),
    PLACE_LOCK_USER_NOT_MATCH(3103, HttpStatus.FORBIDDEN, "해당 장소의 락을 해제할 권한이 없습니다."),

    DATE_VOTING_EXIST(3200, HttpStatus.BAD_REQUEST, "이미 해당 일자에 투표하셨습니다."),
    NO_DATE_VOTING_EXIST(3201, HttpStatus.BAD_REQUEST, "해당 일자에 투표한 이력이 없습니다."),
    DATE_OUT_OF_CALENDAR_RANGE(3202, HttpStatus.BAD_REQUEST, "투표 가능 범위에 해당하지 않는 일자입니다."),
    DATE_RANGE_OUT_OF_CALENDAR_RANGE(3203, HttpStatus.BAD_REQUEST, "모임일 확정 일자가 모임 캘린더 범위에 포함되지 않습니다."),
    MEETING_DATE_ALREADY_EXIST(3204, HttpStatus.BAD_REQUEST, "이미 지정된 모임일이 존재합니다."),
    MEETING_DATE_NOT_EXIST(3205, HttpStatus.BAD_REQUEST, "확정된 모임일이 존재하지 않습니다."),
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
