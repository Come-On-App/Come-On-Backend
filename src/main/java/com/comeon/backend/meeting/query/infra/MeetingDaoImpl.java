package com.comeon.backend.meeting.query.infra;

import com.comeon.backend.common.util.SliceUtils;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dao.dto.EntryCodeDetailsResponse;
import com.comeon.backend.meeting.query.dao.dto.MeetingSliceResponse;
import com.comeon.backend.meeting.query.infra.mapper.MeetingMapper;
import com.comeon.backend.meeting.query.infra.mapper.MeetingSliceParam;
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
    /*
    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    private void validateResult(FindEntryCodeDetailsResult result) {
        checkMeetingExist(result);
        checkUserIsMember(result);
    }

    private void checkUserIsMember(FindEntryCodeDetailsResult result) {
        if (result.getUserId() == null) {
            throw new RestApiException(MeetingErrorCode.NOT_MEMBER);
        }
    }

    private void checkMeetingExist(FindEntryCodeDetailsResult result) {
        if (result == null) {
            throw new RestApiException(MeetingErrorCode.MEETING_NOT_EXIST);
        }
    }
     */
}
