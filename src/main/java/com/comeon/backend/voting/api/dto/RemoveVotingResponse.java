package com.comeon.backend.voting.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveVotingResponse {

    private Boolean success;

    public RemoveVotingResponse() {
        this.success = true;
    }
}
