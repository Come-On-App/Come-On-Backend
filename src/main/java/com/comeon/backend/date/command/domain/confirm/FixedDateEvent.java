package com.comeon.backend.date.command.domain.confirm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FixedDateEvent {

    private Long meetingId;

    public static FixedDateEvent create(Long meetingId) {
        return new FixedDateEvent(meetingId);
    }
}
