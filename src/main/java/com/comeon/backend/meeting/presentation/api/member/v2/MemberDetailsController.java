package com.comeon.backend.meeting.presentation.api.member.v2;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.query.application.v2.MeetingMemberQueryServiceV2;
import com.comeon.backend.meeting.query.dto.MemberDetails;
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
@RequestMapping("/api/v2/meetings/{meetingId}/members")
public class MemberDetailsController {

    private final MeetingMemberQueryServiceV2 meetingMemberQueryService;

    @RequiredMemberRole
    @GetMapping
    public ListResponse<MemberDetails> meetingMemberList(
            @PathVariable Long meetingId
    ) {
        List<MemberDetails> memberList = meetingMemberQueryService.findMemberDetailsList(meetingId);
        return ListResponse.toListResponse(memberList);
    }

    @RequiredMemberRole
    @GetMapping("/me")
    public MemberDetails myMemberInfo(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return meetingMemberQueryService.findMemberDetails(meetingId, jwtPrincipal.getUserId());
    }
}
