package com.comeon.backend.date.api.dto;

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
