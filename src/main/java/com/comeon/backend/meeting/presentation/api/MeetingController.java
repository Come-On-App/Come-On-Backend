package com.comeon.backend.meeting.presentation.api;

import com.comeon.backend.common.response.SliceResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.v1.CreateMeetingFacade;
import com.comeon.backend.meeting.command.application.v1.ModifyMeetingFacade;
import com.comeon.backend.meeting.command.application.v1.dto.MeetingCreateRequest;
import com.comeon.backend.meeting.command.application.v1.dto.MeetingModifyRequest;
import com.comeon.backend.meeting.query.application.MeetingQueryService;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
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
public class MeetingController {

    private final MeetingDao meetingDao;
    private final MeetingQueryService meetingQueryService;
    private final CreateMeetingFacade createMeetingFacade;
    private final ModifyMeetingFacade modifyMeetingFacade;

    @PostMapping
    public MeetingCreateResponse meetingCreate(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingCreateRequest request
    ) {
        Long meetingId = createMeetingFacade.createMeeting(jwtPrincipal.getUserId(), request);
        return new MeetingCreateResponse(meetingId);
    }

    @RequiredMemberRole(MemberRole.HOST)
    @PatchMapping("/{meetingId}")
    public MeetingModifyResponse meetingModify(
            @PathVariable Long meetingId,
            @Validated @RequestBody MeetingModifyRequest request
    ) {
        modifyMeetingFacade.modifyMeetingInfo(meetingId, request);
        return new MeetingModifyResponse();
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
}
