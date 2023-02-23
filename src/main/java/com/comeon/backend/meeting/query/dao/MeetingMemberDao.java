package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dto.MemberDetails;
import com.comeon.backend.meeting.query.dto.MemberSimple;

import java.util.List;

public interface MeetingMemberDao {

    MemberSimple findMemberSimple(Long meetingId, Long userId);

    List<MemberDetails> findMemberDetailsList(Long meetingId);

    MemberDetails findMemberDetails(Long meetingId, Long userId);
}
