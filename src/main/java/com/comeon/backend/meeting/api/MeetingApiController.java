package com.comeon.backend.meeting.api;

import com.comeon.backend.common.config.interceptor.MemberRole;
import com.comeon.backend.common.config.interceptor.RequiredMemberRole;
import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.common.response.SliceResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingCommandDto;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingMemberDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dto.EntryCodeDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingDetailsResponse;
import com.comeon.backend.meeting.query.dto.MeetingSliceResponse;
import com.comeon.backend.meeting.query.dto.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings")
public class MeetingApiController {

    private final MeetingFacade meetingFacade;
    private final MeetingDao meetingDao;
    private final MeetingMemberDao meetingMemberDao;

    @PostMapping
    public MeetingAddResponse meetingAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingCommandDto.AddRequest request
    ) {
        Long meetingId = meetingFacade.addMeeting(jwtPrincipal.getUserId(), request);
        return new MeetingAddResponse(meetingId);
    }

    @GetMapping
    public SliceResponse<MeetingSliceResponse> meetingList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            MeetingSliceCondition param
    ) {
        Slice<MeetingSliceResponse> meetingSlice = meetingDao.findMeetingSlice(jwtPrincipal.getUserId(), pageable, param);
        return SliceResponse.toSliceResponse(meetingSlice);
    }

    @RequiredMemberRole
    @GetMapping("/{meetingId}")
    public MeetingDetailsResponse meetingDetails(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return meetingDao.findMeetingDetails(meetingId, jwtPrincipal.getUserId());
    }

    @RequiredMemberRole
    @GetMapping("/{meetingId}/members")
    public ListResponse<MemberInfoResponse> meetingMemberList(
            @PathVariable Long meetingId
    ) {
        List<MemberInfoResponse> memberList = meetingMemberDao.findMemberList(meetingId);
        return ListResponse.toListResponse(memberList);
    }

    @RequiredMemberRole
    @GetMapping("/{meetingId}/members/me")
    public MemberInfoResponse myMemberInfo(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        MemberInfoResponse memberInfoResponse = meetingMemberDao.findMember(meetingId, jwtPrincipal.getUserId());
        return memberInfoResponse;
    }

    @PostMapping("/join")
    public MeetingCommandDto.JoinResponse meetingJoin(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingCommandDto.JoinRequest request
    ) {
        MeetingCommandDto.JoinResponse response = meetingFacade.joinMeeting(jwtPrincipal.getUserId(), request);
        return response;
    }

    @RequiredMemberRole
    @GetMapping("/{meetingId}/entry-code")
    public EntryCodeDetailsResponse entryCodeDetails(
            @PathVariable Long meetingId
    ) {
        return meetingDao.findEntryCodeDetails(meetingId);
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/{meetingId}/entry-code")
    public MeetingCommandDto.RenewEntryCodeResponse entryCodeRenew(
            @PathVariable Long meetingId
    ) {
        MeetingCommandDto.RenewEntryCodeResponse response = meetingFacade.renewEntryCode(meetingId);
        return response;
    }
}
