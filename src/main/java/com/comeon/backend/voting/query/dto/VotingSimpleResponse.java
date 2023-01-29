package com.comeon.backend.voting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VotingSimpleResponse {

    private LocalDate date;
    private int memberCount;
    private boolean myVoting;
}
