package com.comeon.backend.date.command.domain.confirm;

import java.util.Optional;

public interface FixedDateRepository {

    FixedDate save(FixedDate fixedDate);
    Optional<FixedDate> findFixedDateByMeetingId(Long meetingId);
    void remove(FixedDate fixedDate);
}
