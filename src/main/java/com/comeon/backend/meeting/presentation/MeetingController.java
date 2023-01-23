package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.api.SliceResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.command.application.MeetingFacade;
import com.comeon.backend.meeting.command.application.MeetingMemberFacade;
import com.comeon.backend.meeting.command.application.dto.MeetingMemberSummary;
import com.comeon.backend.meeting.presentation.request.MeetingAddRequest;
import com.comeon.backend.meeting.presentation.request.MeetingJoinRequest;
import com.comeon.backend.meeting.presentation.request.MeetingSummaryListParam;
import com.comeon.backend.meeting.presentation.response.MeetingAddResponse;
import com.comeon.backend.meeting.presentation.response.MeetingJoinResponse;
import com.comeon.backend.meeting.presentation.response.MeetingSummaryResponse;
import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dao.MeetingCondition;
import com.comeon.backend.meeting.query.dao.result.FindMeetingSliceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private final MeetingFacade meetingFacade;
    private final MeetingMemberFacade meetingMemberFacade;
    private final MeetingDao meetingDao;

    @PostMapping
    public MeetingAddResponse meetingAdd(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                         @Validated @RequestBody MeetingAddRequest request) {
        Long meetingId = meetingFacade.addMeeting(
                jwtPrincipal.getUserId(),
                request.getMeetingName(),
                request.getMeetingImageUrl(),
                request.getCalendarStartFrom(),
                request.getCalendarEndTo()
        );

        return new MeetingAddResponse(meetingId);
    }

    @GetMapping
    public SliceResponse<MeetingSummaryResponse> meetingSummaryList(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                                                    @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                                    MeetingSummaryListParam param) {
        // TODO 리팩토링
        Slice<FindMeetingSliceResult> meetingSlice = meetingDao.findMeetingSlice(
                jwtPrincipal.getUserId(),
                pageable,
                new MeetingCondition(
                        param.getSearchWords(),
                        param.getDateFrom(),
                        param.getDateTo()
                )
        );

        return SliceResponse.toSliceResponse(
                meetingSlice.map(
                        result -> new MeetingSummaryResponse(
                                result.getMeetingId(),
                                new MeetingSummaryResponse.UserSummaryResponse(
                                        result.getHostUserId(),
                                        result.getHostUserNickname(),
                                        result.getHostUserProfileImageUrl()
                                ),
                                result.getMemberCount(),
                                result.getMyMeetingRole(),
                                result.getMeetingName(),
                                LocalDate.parse(result.getCalendarStartFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                LocalDate.parse(result.getCalendarEndTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                result.getMeetingImageUrl(),
                                null,
                                null
                        )
                )
        );
    }

    @PostMapping("/join")
    public MeetingJoinResponse meetingJoin(@AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                           @Validated @RequestBody MeetingJoinRequest request) {
        MeetingMemberSummary meetingMemberSummary = meetingMemberFacade.joinMeeting(jwtPrincipal.getUserId(), request.getEntryCode());
        return new MeetingJoinResponse(meetingMemberSummary.getMeetingId(), meetingMemberSummary.getMemberId(), meetingMemberSummary.getMemberRole());
    }
}
