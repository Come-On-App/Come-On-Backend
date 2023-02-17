package com.comeon.backend.date.infrastructure.dao;

import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;
import com.comeon.backend.date.query.dto.MeetingFixedDateSummary;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FixedDateMapper {

    MeetingFixedDateSimple selectFixedDateSimple(Long meetingId);
    List<MeetingFixedDateSummary> selectMeetingFixedDateSummary(Long userId, LocalDate searchStartFrom, LocalDate searchEndTo);
}
