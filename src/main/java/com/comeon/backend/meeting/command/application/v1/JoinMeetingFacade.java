package com.comeon.backend.meeting.command.application.v1;

import com.comeon.backend.meeting.command.application.v1.dto.MeetingJoinResponse;
import com.comeon.backend.meeting.command.domain.Meeting;
import com.comeon.backend.meeting.command.domain.MeetingRepository;
import com.comeon.backend.meeting.command.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinMeetingFacade {

    private final MeetingRepository meetingRepository;

    public MeetingJoinResponse joinMeeting(Long userId, String entryCode) {
        Meeting meeting = meetingRepository.findMeetingBy(entryCode)
                .orElseThrow(EntryCodeNotMatchedException::new);
        Member member = meeting.join(userId);

        meetingRepository.flush();

        return new MeetingJoinResponse(member);
    }
}
