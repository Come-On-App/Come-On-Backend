package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetails {

    private Long memberId;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String memberRole;

    public void setProfileImageUrl(String defaultImageUrl) {
        this.profileImageUrl = defaultImageUrl;
    }
}
