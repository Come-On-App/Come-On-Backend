package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberListResponse {

    private Long memberId;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String memberRole;
}
