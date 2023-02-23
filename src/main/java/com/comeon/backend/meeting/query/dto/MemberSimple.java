package com.comeon.backend.meeting.query.dto;

import com.comeon.backend.meeting.command.domain.MemberRole;
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
