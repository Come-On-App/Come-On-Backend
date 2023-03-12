package com.comeon.backend.meeting.query.application.v2;

import com.comeon.backend.meeting.command.application.v1.MeetingNotExistException;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import com.comeon.backend.meeting.query.dto.MeetingSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingQueryServiceV2 {

    @Value("${user.default-image}")
    private String defaultImageUrl;

    private final MeetingDao meetingDao;

    public MeetingDetails findMeetingDetails(Long meetingId, Long userId) {
        MeetingDetails meetingDetails = meetingDao.findMeetingDetails(meetingId, userId);

        if (meetingDetails == null) {
            throw new MeetingNotExistException(meetingId);
        }

        injectDefaultProfileImageUrl(meetingDetails);

        return meetingDetails;
    }

    private void injectDefaultProfileImageUrl(MeetingDetails meetingDetails) {
        if (meetingDetails.getMeetingMetaData().getHostUser().getProfileImageUrl() == null) {
            meetingDetails.setHostUserProfileImageUrl(defaultImageUrl);
        }

        meetingDetails.getMembers().stream()
                .filter(memberDetails -> memberDetails.getProfileImageUrl() == null)
                .forEach(memberDetails -> memberDetails.setProfileImageUrl(defaultImageUrl));
    }

    public Slice<MeetingSimple> findMeetingSlice(Long userId, Pageable pageable, MeetingSliceCondition condition) {
        return meetingDao.findMeetingSlice(userId, pageable, condition)
                .map(meetingSimple -> {
                    if (meetingSimple.getHostUser().getProfileImageUrl() == null) {
                        meetingSimple.setHostUserProfileImageUrl(defaultImageUrl);
                    }
                    return meetingSimple;
                });
    }
}
