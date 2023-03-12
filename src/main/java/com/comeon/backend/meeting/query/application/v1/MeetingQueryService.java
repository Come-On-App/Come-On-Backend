package com.comeon.backend.meeting.query.application.v1;

import com.comeon.backend.meeting.command.application.v1.EntryCodeNotMatchedException;
import com.comeon.backend.meeting.command.application.v1.MeetingNotExistException;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingQueryService {

    private final MeetingDao meetingDao;

    public MeetingDetails findMeetingDetails(Long meetingId, Long userId) {
        MeetingDetails meetingDetails = meetingDao.findMeetingDetails(meetingId, userId);

        if (meetingDetails == null) {
            throw new MeetingNotExistException(meetingId);
        }

        return meetingDetails;
    }

    public Long findMeetingId(String entryCode) {
        Long meetingId = meetingDao.findMeetingIdByEntryCode(entryCode);

        if (meetingId == null) {
            throw new EntryCodeNotMatchedException();
        }

        return meetingId;
    }
}
