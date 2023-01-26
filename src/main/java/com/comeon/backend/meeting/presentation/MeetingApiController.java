package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.api.SliceResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.command.application.dto.MeetingCommandDto;
import com.comeon.backend.meeting.presentation.response.MeetingAddResponse;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.dto.MeetingSliceResponse;
import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
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

    // TODO 유저 접근 권한 관리 - Security
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

    @PostMapping("/join")
    public MeetingCommandDto.JoinResponse meetingJoin(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @Validated @RequestBody MeetingCommandDto.JoinRequest request
    ) {
        MeetingCommandDto.JoinResponse response = meetingFacade.joinMeeting(jwtPrincipal.getUserId(), request);
        return response;
    }
}
