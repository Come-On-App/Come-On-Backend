package com.comeon.backend.meetingmember.query.dao;

import com.comeon.backend.meetingmember.query.dto.MemberDetails;
import com.comeon.backend.meetingmember.query.dto.MemberSimple;

import java.util.List;

public interface MeetingMemberDao {

    MemberSimple findMemberSimple(Long meetingId, Long userId);

    List<MemberDetails> findMemberDetailsList(Long meetingId);

    MemberDetails findMemberDetails(Long meetingId, Long userId);
}
