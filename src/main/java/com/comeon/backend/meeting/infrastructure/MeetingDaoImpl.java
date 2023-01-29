package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.common.util.SliceUtils;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dto.EntryCodeDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingSliceResponse;
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
    public Slice<MeetingSliceResponse> findMeetingSlice(Long userId, Pageable pageable, MeetingSliceCondition cond) {
        MeetingSliceParam param = new MeetingSliceParam(userId, cond, pageable);
        List<MeetingSliceResponse> slice = meetingMapper.selectMeetingSlice(param);
        return new SliceImpl<>(slice, pageable, SliceUtils.hasNext(pageable, slice));
    }

    @Override
    public EntryCodeDetailsResponse findEntryCodeDetails(Long meetingId) {
        return meetingMapper.selectEntryCodeDetails(meetingId);
    }

    @Override
    public MeetingDetailsResponse findMeetingDetails(Long meetingId) {
        return meetingMapper.selectMeetingDetails(meetingId);
    }
}
