package com.comeon.backend.meeting.command.application;

import com.comeon.backend.meeting.command.domain.EntryCode;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeetingCommandDto {

    @NoArgsConstructor
    public static class AddRequest {
        @NotBlank
        private String meetingName;

        @NotBlank
        private String meetingImageUrl;

        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
        @NotBlank
        private String calendarStartFrom;

        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
        @NotBlank
        private String calendarEndTo;

        public String getMeetingName() {
            return meetingName;
        }

        public String getMeetingImageUrl() {
            return meetingImageUrl;
        }

        public LocalDate getCalendarStartFrom() {
            return LocalDate.parse(calendarStartFrom, DateTimeFormatter.ISO_DATE);
        }

        public LocalDate getCalendarEndTo() {
            return LocalDate.parse(calendarEndTo, DateTimeFormatter.ISO_DATE);
        }

        public AddRequest(String meetingName, String meetingImageUrl, LocalDate calendarStartFrom, LocalDate calendarEndTo) {
            this.meetingName = meetingName;
            this.meetingImageUrl = meetingImageUrl;
            this.calendarStartFrom = calendarStartFrom.format(DateTimeFormatter.ISO_DATE);
            this.calendarEndTo = calendarEndTo.format(DateTimeFormatter.ISO_DATE);
        }

        public Meeting toEntity(Long userId) {
            return Meeting.builder()
                    .hostUserId(userId)
                    .name(meetingName)
                    .thumbnailImageUrl(meetingImageUrl)
                    .calendarStartFrom(getCalendarStartFrom())
                    .calendarEndTo(getCalendarEndTo())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinRequest {
        @NotBlank
        private String entryCode;
    }

    @Getter
    public static class JoinResponse {
        private Long meetingId;
        private MemberSimple meetingMember;

        public JoinResponse(Member member) {
            this.meetingId = member.getMeeting().getId();
            this.meetingMember = new MemberSimple(member.getId(), member.getRole().name());
        }

        public JoinResponse(Long meetingId, Long memberId, String memberRole) {
            this.meetingId = meetingId;
            this.meetingMember = new MemberSimple(memberId, memberRole);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class MemberSimple {
        private Long memberId;
        private String memberRole;
    }

    @Getter
    @AllArgsConstructor
    public static class RenewEntryCodeResponse {
        private Long meetingId;
        private String entryCode;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime expiredAt;

        public RenewEntryCodeResponse(EntryCode entryCode) {
            this.meetingId = entryCode.getMeeting().getId();
            this.entryCode = entryCode.getCode();
            this.expiredAt = entryCode.getLastModifiedDate().plusDays(7);
        }
    }
}
