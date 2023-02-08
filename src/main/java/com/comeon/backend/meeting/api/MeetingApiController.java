package com.comeon.backend.meeting.api;

import com.comeon.backend.common.response.SliceResponse;
import com.comeon.backend.config.web.member.MemberRole;
import com.comeon.backend.config.web.member.RequiredMemberRole;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.command.application.dto.EntryCodeRenewResponse;
import com.comeon.backend.meeting.command.application.dto.MeetingAddRequest;
import com.comeon.backend.meeting.command.application.dto.MeetingTimeModifyRequest;
import com.comeon.backend.meeting.query.application.MeetingQueryService;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dto.EntryCodeDetails;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import com.comeon.backend.meeting.query.dto.MeetingSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings")
public class MeetingApiController {

    private final MeetingFacade meetingFacade;
    private final MeetingDao meetingDao;
    private final MeetingQueryService meetingQueryService;

    @PostMapping
    public MeetingAddResponse meetingAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingAddRequest request
    ) {
        Long meetingId = meetingFacade.addMeeting(jwtPrincipal.getUserId(), request);
        return new MeetingAddResponse(meetingId);
    }

    @GetMapping
    public SliceResponse<MeetingSimple> meetingList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            MeetingSliceCondition param
    ) {
        Slice<MeetingSimple> meetingSlice = meetingDao.findMeetingSlice(jwtPrincipal.getUserId(), pageable, param);
        return SliceResponse.toSliceResponse(meetingSlice);
    }

    @RequiredMemberRole
    @GetMapping("/{meetingId}")
    public MeetingDetails meetingDetails(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        return meetingQueryService.findMeetingDetails(meetingId, jwtPrincipal.getUserId());
    }

    @RequiredMemberRole
    @GetMapping("/{meetingId}/entry-code")
    public EntryCodeDetails entryCodeDetails(
            @PathVariable Long meetingId
    ) {
        return meetingDao.findEntryCodeDetails(meetingId);
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/{meetingId}/entry-code")
    public EntryCodeRenewResponse entryCodeRenew(
            @PathVariable Long meetingId
    ) {
        return meetingFacade.renewEntryCode(meetingId);
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PostMapping("/{meetingId}/meeting-time")
    public MeetingTimeModifyResponse meetingTimeModify(
            @PathVariable Long meetingId,
            @Validated @RequestBody MeetingTimeModifyRequest request
    ) {
        meetingFacade.modifyMeetingTime(meetingId, request);
        return new MeetingTimeModifyResponse();
    }
}
