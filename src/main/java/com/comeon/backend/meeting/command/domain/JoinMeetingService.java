package com.comeon.backend.meeting.command.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinMeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingMember joinMeetingBy(String entryCode, Long userId) {
        Meeting meeting = meetingRepository.findMeetingBy(entryCode)
                .orElseThrow(EntryCodeNotMatchedException::new);
        checkMemberExist(userId, meeting);

        MeetingMember member = meeting.join(userId);
        meetingRepository.flush();

        return member;
    }

    private void checkMemberExist(Long userId, Meeting meeting) {
        meetingRepository.findMeetingMemberBy(meeting.getId(), userId)
                .ifPresent(this::generateJoinedError);
    }

    private void generateJoinedError(MeetingMember meetingMember) {
        throw new MemberAlreadyJoinedException(meetingMember);
    }
}
