package com.comeon.backend.common.config.interceptor;

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
