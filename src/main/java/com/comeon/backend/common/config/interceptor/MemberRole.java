package com.comeon.backend.common.config.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
@Getter
@AllArgsConstructor
public enum MemberRole {
    HOST("모임 방장 권한"),
    PARTICIPANT("모임 참가자 권한"),
    ;

    private final String description;

    public static Optional<MemberRole> of(String name) {
        for (MemberRole memberRole : MemberRole.values()) {
            if (memberRole.name().equalsIgnoreCase(name)) {
                return Optional.of(memberRole);
            }
        }
        return Optional.empty();
    }
}
