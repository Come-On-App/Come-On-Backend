package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntryCode {

    @Column(name = "entry_code")
    private String code;

    @Column(name = "entry_code_updated_at")
    private LocalDateTime updatedAt;

    public static EntryCode create() {
        return new EntryCode(randomCode(), LocalDateTime.now());
    }

    private EntryCode(String code, LocalDateTime updatedAt) {
        this.code = code;
        this.updatedAt = updatedAt;
    }

    private static String randomCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(updatedAt);
    }
}
