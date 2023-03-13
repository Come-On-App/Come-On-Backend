package com.comeon.backend.meeting.presentation.api.meeting.v2;

import com.comeon.backend.common.response.SliceResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.query.application.v2.MeetingQueryServiceV2;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import com.comeon.backend.meeting.query.dto.MeetingDetails;
import com.comeon.backend.meeting.query.dto.MeetingSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/meetings")
public class MeetingQueryController {

    private final MeetingQueryServiceV2 meetingQueryService;

    @GetMapping
    public SliceResponse<MeetingSimple> meetingList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            MeetingSliceCondition param
    ) {
        Slice<MeetingSimple> meetingSlice = meetingQueryService.findMeetingSlice(jwtPrincipal.getUserId(), pageable, param);
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
