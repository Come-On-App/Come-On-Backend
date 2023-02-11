package com.comeon.backend.date.infrastructure.dao;

import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FixedDateMapper {

    MeetingFixedDateSimple selectFixedDateSimple(Long meetingId);
}
