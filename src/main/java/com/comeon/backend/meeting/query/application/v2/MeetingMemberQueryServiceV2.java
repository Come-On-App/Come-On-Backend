package com.comeon.backend.meeting.query.application.v2;

import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingMemberQueryServiceV2 {

    @Value("${user.default-image}")
    private String defaultImageUrl;

    private final MeetingMemberDao meetingMemberDao;

    public List<MemberDetails> findMemberDetailsList(Long meetingId) {
        List<MemberDetails> memberDetailsList = meetingMemberDao.findMemberDetailsList(meetingId);
        for (MemberDetails memberDetails : memberDetailsList) {
            setDefaultProfileImageIfNull(memberDetails);
        }

        return memberDetailsList;
    }

    public MemberDetails findMemberDetails(Long meetingId, Long userId) {
        MemberDetails memberDetails = meetingMemberDao.findMemberDetails(meetingId, userId);
        setDefaultProfileImageIfNull(memberDetails);

        return memberDetails;
    }

    private void setDefaultProfileImageIfNull(MemberDetails memberDetails) {
        if (memberDetails.getProfileImageUrl() == null) {
            memberDetails.setProfileImageUrl(defaultImageUrl);
        }
    }
}
