package com.comeon.backend.meeting.presentation.api.meetingdate;

import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.config.security.JwtPrincipal;
import com.comeon.backend.meeting.presentation.interceptor.RequiredMemberRole;
import com.comeon.backend.meeting.command.application.VotingDateFacade;
import com.comeon.backend.meeting.query.application.DateVotingQueryService;
import com.comeon.backend.meeting.query.dao.DateVotingDao;
import com.comeon.backend.meeting.query.dto.DateVotingDetails;
import com.comeon.backend.meeting.query.dto.DateVotingSimple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings/{meetingId}/date/voting")
public class MeetingDateVotingController {

    private final DateVotingDao dateVotingDao;
    private final DateVotingQueryService dateVotingQueryService;
    private final VotingDateFacade votingDateFacade;

    @RequiredMemberRole
    @PostMapping
    public VotingAddResponse votingAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @Validated @RequestBody VotingAddRequest request
    ) {
        votingDateFacade.addVoting(jwtPrincipal.getUserId(), meetingId, request.getDate());
        return new VotingAddResponse();
    }

    @RequiredMemberRole
    @DeleteMapping
    public VotingRemoveResponse votingRemove(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @Validated @RequestBody VotingRemoveRequest request
    ) {
        votingDateFacade.removeVoting(jwtPrincipal.getUserId(), meetingId, request.getDate());
        return new VotingRemoveResponse();
    }

    // 전체 투표 기간 요약 정보 조회
    @RequiredMemberRole
    @GetMapping
    public ListResponse<DateVotingSimple> votingSimpleList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        List<DateVotingSimple> votingSimples
                = dateVotingDao.findVotingSimpleListByMeetingIdWhetherMyVoting(meetingId, jwtPrincipal.getUserId());
        return ListResponse.toListResponse(votingSimples);
    }

    // 특정 일자 투표 현황 상세 조회
    @RequiredMemberRole
    @GetMapping("/details")
    public DateVotingDetails votingDetails(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return dateVotingQueryService.votingDetails(meetingId, date, jwtPrincipal.getUserId());
    }
}
