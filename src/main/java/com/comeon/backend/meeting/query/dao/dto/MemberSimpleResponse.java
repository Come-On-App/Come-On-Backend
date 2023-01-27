package com.comeon.backend.meeting.query.dao.dto;

import com.comeon.backend.meeting.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSimpleResponse {

    private Long userId;
    private Long memberId;
    private MemberRole memberRole;
}
