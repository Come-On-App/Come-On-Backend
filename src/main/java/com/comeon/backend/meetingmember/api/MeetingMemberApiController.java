package com.comeon.backend.meetingmember.api;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.config.web.member.MemberRole;
import com.comeon.backend.config.web.member.RequiredMemberRole;
import com.comeon.backend.meetingmember.api.dto.MeetingLeaveResponse;
import com.comeon.backend.meetingmember.api.dto.MemberDropResponse;
import com.comeon.backend.meetingmember.command.application.MeetingMemberFacade;
import com.comeon.backend.meetingmember.api.dto.MemberDropRequest;
import com.comeon.backend.meetingmember.query.dao.MeetingMemberDao;
import com.comeon.backend.meetingmember.query.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/members")
public class MeetingMemberApiController {

    private final MeetingMemberFacade meetingMemberFacade;
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

    @RequiredMemberRole
    @DeleteMapping("/me")
    public MeetingLeaveResponse meetingLeave(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        meetingMemberFacade.removeMember(meetingId, jwtPrincipal.getUserId());
        return new MeetingLeaveResponse();
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/drop")
    public MemberDropResponse memberDrop(
            @PathVariable Long meetingId,
            @Validated @RequestBody MemberDropRequest request
    ) {
        meetingMemberFacade.removeMember(meetingId, request.getTargetUserId());
        return new MemberDropResponse();
    }
}
