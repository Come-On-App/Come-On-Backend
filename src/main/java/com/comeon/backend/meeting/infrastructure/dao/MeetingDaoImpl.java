package com.comeon.backend.meeting.infrastructure.dao;

import com.comeon.backend.common.utils.SliceUtils;
import com.comeon.backend.meeting.infrastructure.mapper.MeetingSliceParam;
import com.comeon.backend.meeting.infrastructure.mapper.MeetingMapper;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dto.EntryCodeDetails;
import com.comeon.backend.meeting.query.dto.MeetingCalendarDetails;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import com.comeon.backend.meeting.query.dto.MeetingSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingDaoImpl implements MeetingDao {

    private final MeetingMapper meetingMapper;

    @Override
    public Slice<MeetingSimple> findMeetingSlice(Long userId, Pageable pageable, MeetingSliceCondition cond) {
        MeetingSliceParam param = new MeetingSliceParam(userId, cond, pageable);
        List<MeetingSimple> slice = meetingMapper.selectMeetingSlice(param);
        return new SliceImpl<>(slice, pageable, SliceUtils.hasNext(pageable, slice));
    }

    @Override
    public EntryCodeDetails findEntryCodeDetails(Long meetingId) {
        return meetingMapper.selectEntryCodeDetails(meetingId);
    }

    @Override
    public MeetingDetails findMeetingDetails(Long meetingId, Long userId) {
        return meetingMapper.selectMeetingDetails(meetingId, userId);
    }

    @Override
    public Long findMeetingIdByEntryCode(String entryCode) {
        return meetingMapper.selectMeetingId(entryCode);
    }

    @Override
    public MeetingCalendarDetails findMeetingCalendar(Long meetingId) {
        return meetingMapper.selectMeetingCalendar(meetingId);
    }
}
