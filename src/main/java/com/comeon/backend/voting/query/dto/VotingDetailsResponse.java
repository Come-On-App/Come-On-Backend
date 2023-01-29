package com.comeon.backend.voting.query.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class VotingDetailsResponse {

    private LocalDate date;
    private boolean myVoting;
    private int memberCount;
    private List<VotingMemberSimple> votingUsers;

    public VotingDetailsResponse(LocalDate date, boolean myVoting, List<VotingMemberSimple> votingUsers) {
        this.date = date;
        this.myVoting = myVoting;
        this.memberCount = votingUsers.size();
        this.votingUsers = votingUsers;
    }
}
