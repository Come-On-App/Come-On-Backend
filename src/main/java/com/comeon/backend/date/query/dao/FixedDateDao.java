package com.comeon.backend.date.query.dao;

import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;

public interface FixedDateDao {

    MeetingFixedDateSimple findFixedDateSimple(Long meetingId);
}
