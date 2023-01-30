package com.comeon.backend.date.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddVotingResponse {

    private Boolean success;

    public AddVotingResponse() {
        this.success = true;
    }
}
