package com.comeon.backend.meeting.query.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.meeting.common.MeetingErrorCode;
import com.comeon.backend.meeting.query.dao.param.FindEntryCodeDetailsParam;
import com.comeon.backend.meeting.query.dao.result.FindEntryCodeDetailsResult;
import com.comeon.backend.meeting.query.dao.MeetingEntryCodeDao;
import com.comeon.backend.meeting.query.application.dto.MeetingEntryCodeDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingQueryService {

    private final MeetingEntryCodeDao meetingEntryCodeDao;

    public MeetingEntryCodeDetails findMeetingEntryCodeDetails(Long meetingId, Long userId) {
        FindEntryCodeDetailsResult result = meetingEntryCodeDao.findEntryCodeDetailsByMeetingId(new FindEntryCodeDetailsParam(meetingId, userId));
        validateResult(result);

        LocalDateTime expiredAt = result.getLastModifiedDate() != null
                ? LocalDateTime.parse(result.getLastModifiedDate(), getDateTimeFormatter()).plusDays(7)
                : null;

        return new MeetingEntryCodeDetails(result.getMeetingId(), result.getEntryCode(), expiredAt);
    }

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
}
