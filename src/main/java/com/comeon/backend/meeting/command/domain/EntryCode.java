package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter
@Table(name = "meeting_entry_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntryCode extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_entry_code_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private String code;

    private EntryCode(Meeting meeting) {
        this.meeting = meeting;
        this.code = randomCode();
    }

    public static EntryCode createWithRandomCode(Meeting meeting) {
        return new EntryCode(meeting);
    }

    public void renewCode() {
        this.code = randomCode();
    }

    private String randomCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(lastModifiedDate);
    }
}
