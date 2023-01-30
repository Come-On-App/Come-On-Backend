package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostUserSimpleResponse {

    private Long userId;
    private String nickname;
    private String profileImageUrl;
}
