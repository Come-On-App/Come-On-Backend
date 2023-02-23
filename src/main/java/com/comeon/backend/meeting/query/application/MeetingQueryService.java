package com.comeon.backend.meeting.query.application;

import com.comeon.backend.meeting.command.application.EntryCodeNotMatchedException;
import com.comeon.backend.meeting.command.application.MeetingNotExistException;
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
