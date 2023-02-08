package com.comeon.backend.meetingmember.query.dto;

import com.comeon.backend.config.web.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSimple {

    private Long userId;
    private Long memberId;
    private MemberRole memberRole;
}
