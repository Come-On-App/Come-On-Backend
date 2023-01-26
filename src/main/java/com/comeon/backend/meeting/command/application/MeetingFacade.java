package com.comeon.backend.meeting.command.application;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.meeting.command.application.dto.MeetingCommandDto;
import com.comeon.backend.meeting.command.domain.*;
import com.comeon.backend.meeting.MeetingErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingFacade {

    private final MeetingRepository meetingRepository;

    public Long addMeeting(Long userId, MeetingCommandDto.AddRequest request) {
        Meeting meeting = request.toEntity(userId);
        return meetingRepository.saveMeeting(meeting).getId();
    }

    public MeetingCommandDto.JoinResponse joinMeeting(Long userId, MeetingCommandDto.JoinRequest request) {
        // TODO 예외 작성
        Meeting meeting = meetingRepository.findMeetingBy(request.getEntryCode())
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.ENTRY_CODE_NOT_MATCHED));
        checkMemberExist(userId, meeting);

        MeetingMember member = meeting.join(userId);
        meetingRepository.flush();

        return new MeetingCommandDto.JoinResponse(member);
    }

    private void checkMemberExist(Long userId, Meeting meeting) {
        meetingRepository.findMeetingMemberBy(meeting.getId(), userId)
                .ifPresent(meetingMember -> {
                    throw new RestApiException("이미 모임에 가입된 유저입니다. meeting-id: " + meetingMember.getMeeting().getId()
                            + ", user-id: " + meetingMember.getUserId(), MeetingErrorCode.ALREADY_JOINED);
                });
    }

    public MeetingCommandDto.RenewEntryCodeResponse renewEntryCode(Long meetingId, Long userId) {
        Meeting meeting = meetingRepository.findMeetingBy(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_EXIST));
        checkHostUser(meetingId, userId);
        MeetingEntryCode entryCode = meeting.renewEntryCodeAndGet();

        return new MeetingCommandDto.RenewEntryCodeResponse(entryCode);
    }

    private void checkHostUser(Long meetingId, Long userId) {
        meetingRepository.findMeetingMemberBy(meetingId, userId)
                .ifPresentOrElse(
                        // 모임에 가입된 유저이면 권한 확인
                        meetingMember -> {
                            // 모임에 가입된 유저가 방장이 아니면 예외
                            if (!meetingMember.isHost()) {
                                throw new RestApiException(MeetingErrorCode.NO_AUTHORITIES);
                            }
                        },
                        // 가입되지 않으면 예외
                        () -> {
                            throw new RestApiException(MeetingErrorCode.NOT_MEMBER);
                        }
                );
    }
}
