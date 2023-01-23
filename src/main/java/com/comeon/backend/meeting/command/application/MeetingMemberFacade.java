package com.comeon.backend.meeting.command.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.meeting.command.application.dto.MeetingMemberSummary;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingMember;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import com.comeon.backend.meeting.common.MeetingErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingMemberFacade {

    private final MeetingRepository meetingRepository;

    public MeetingMemberSummary joinMeeting(Long userId, String entryCode) {
        Meeting meeting = meetingRepository.findMeetingBy(entryCode)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.ENTRY_CODE_NOT_MATCHED));

        meetingRepository.findMeetingMemberBy(meeting.getId(), userId)
                .ifPresent(meetingMember -> {
                    throw new RestApiException("이미 모임에 가입된 유저입니다. meeting-id: " + meetingMember.getMeeting().getId()
                            + ", user-id: " + meetingMember.getUserId(), MeetingErrorCode.ALREADY_JOINED);
                });
        MeetingMember meetingMember = meetingRepository.saveMeetingMember(meeting.join(userId));

        return new MeetingMemberSummary(meeting.getId(), meetingMember.getId(), meetingMember.getRole().name());
    }
}
