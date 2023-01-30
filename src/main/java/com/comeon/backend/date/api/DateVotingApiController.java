package com.comeon.backend.date.api;

import com.comeon.backend.common.config.interceptor.RequiredMemberRole;
import com.comeon.backend.common.response.ListResponse;
import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.date.api.dto.AddVotingResponse;
import com.comeon.backend.date.api.dto.RemoveVotingResponse;
import com.comeon.backend.date.command.application.voting.AddVotingRequest;
import com.comeon.backend.date.command.application.voting.RemoveVotingRequest;
import com.comeon.backend.date.command.application.voting.VotingFacade;
import com.comeon.backend.date.query.application.DateVotingQueryService;
import com.comeon.backend.date.query.dao.DateVotingDao;
import com.comeon.backend.date.query.dto.VotingDetailsResponse;
import com.comeon.backend.date.query.dto.VotingSimpleResponse;
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
public class DateVotingApiController {


    private final VotingFacade votingFacade;
    private final DateVotingDao dateVotingDao;
    private final DateVotingQueryService dateVotingQueryService;

    @RequiredMemberRole
    @PostMapping
    public AddVotingResponse votingAdd(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @Validated @RequestBody AddVotingRequest request
    ) {
        votingFacade.addVoting(jwtPrincipal.getUserId(), meetingId, request);
        return new AddVotingResponse();
    }

    @RequiredMemberRole
    @DeleteMapping
    public RemoveVotingResponse votingRemove(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @Validated @RequestBody RemoveVotingRequest request
    ) {
        votingFacade.removeVoting(jwtPrincipal.getUserId(), meetingId, request);
        return new RemoveVotingResponse();
    }

    // 전체 투표 기간 요약 정보 조회
    @RequiredMemberRole
    @GetMapping
    public ListResponse<VotingSimpleResponse> votingSimpleList(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId
    ) {
        List<VotingSimpleResponse> votingSimples
                = dateVotingDao.findVotingSimpleListByMeetingIdWhetherMyVoting(meetingId, jwtPrincipal.getUserId());
        return ListResponse.toListResponse(votingSimples);
    }

    // 특정 일자 투표 현황 상세 조회
    @RequiredMemberRole
    @GetMapping("/details")
    public VotingDetailsResponse votingDetails(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long meetingId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        VotingDetailsResponse response = dateVotingQueryService.votingDetails(meetingId, date, jwtPrincipal.getUserId());
        return response;
    }
}
