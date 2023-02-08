package com.comeon.backend.meetingmember.api;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.config.web.member.RequiredMemberRole;
import com.comeon.backend.meetingmember.query.dao.MeetingMemberDao;
import com.comeon.backend.meetingmember.query.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/members")
public class MeetingMemberApiController {

    private final MeetingMemberDao meetingMemberDao;

    @RequiredMemberRole
    @GetMapping
    public ListResponse<MemberDetails> meetingMemberList(
            @PathVariable Long meetingId
    ) {
        List<MemberDetails> memberList = meetingMemberDao.findMemberDetailsList(meetingId);
        return ListResponse.toListResponse(memberList);
    }

    @RequiredMemberRole
    @GetMapping("/me")
    public MemberDetails myMemberInfo(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return meetingMemberDao.findMemberDetails(meetingId, jwtPrincipal.getUserId());
    }
}
