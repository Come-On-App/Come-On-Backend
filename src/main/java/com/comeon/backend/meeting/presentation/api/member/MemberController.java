package com.comeon.backend.meeting.presentation.api.member;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.v1.DelegateHostRoleFacade;
import com.comeon.backend.meeting.command.application.v1.DropMemberFacade;
import com.comeon.backend.meeting.command.application.v1.LeaveMeetingFacade;
import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dto.MemberDetails;
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
public class MemberController {

    private final MeetingMemberDao meetingMemberDao;
    private final LeaveMeetingFacade leaveMeetingFacade;
    private final DelegateHostRoleFacade delegateHostRoleFacade;
    private final DropMemberFacade dropMemberFacade;

    @RequiredMemberRole
    @DeleteMapping("/me")
    public MeetingLeaveResponse meetingLeave(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        leaveMeetingFacade.leaveMeeting(meetingId, jwtPrincipal.getUserId());
        return new MeetingLeaveResponse();
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/host-change")
    public HostRoleDelegateResponse hostRoleDelegate(
            @PathVariable Long meetingId,
            @Validated @RequestBody HostRoleDelegateRequest request
    ) {
        delegateHostRoleFacade.delegateHostRole(meetingId, request.getTargetUserId());
        return new HostRoleDelegateResponse();
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/drop")
    public MemberDropResponse memberDrop(
            @PathVariable Long meetingId,
            @Validated @RequestBody MemberDropRequest request
    ) {
        dropMemberFacade.dropMember(meetingId, request.getTargetUserId());
        return new MemberDropResponse();
    }

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
