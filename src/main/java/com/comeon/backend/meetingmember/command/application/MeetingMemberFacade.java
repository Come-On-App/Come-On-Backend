package com.comeon.backend.meetingmember.command.application;

import com.comeon.backend.meetingmember.command.application.dto.MeetingJoinRequest;
import com.comeon.backend.meetingmember.command.application.dto.MeetingJoinResponse;
import com.comeon.backend.meetingmember.command.domain.MeetingMember;
import com.comeon.backend.meetingmember.command.domain.MeetingMemberRepository;
import com.comeon.backend.meetingmember.command.domain.MeetingService;
import com.comeon.backend.meetingmember.query.application.NotMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeetingMemberFacade {

    private final MeetingService meetingService;
    private final MeetingMemberRepository meetingMemberRepository;

    public MeetingJoinResponse joinMeeting(Long userId, MeetingJoinRequest request) {
        Long meetingId = meetingService.getMeetingIdByEntryCode(request.getEntryCode());
        meetingMemberRepository.findMember(meetingId, userId)
                .ifPresent(member -> {
                    throw new MemberAlreadyJoinedException(member);
                });

        MeetingMember member = MeetingMember.createParticipantMember(meetingId, userId);
        meetingMemberRepository.save(member);

        return new MeetingJoinResponse(member);
    }

    public void createHostUser(Long userId, Long meetingId) {
        log.debug("HOST member creating start.. meetingId: {}, userId: {}", meetingId, userId);
        List<MeetingMember> memberList = meetingMemberRepository.findMemberListByMeetingId(meetingId);
        memberList.stream()
                .filter(MeetingMember::isHost)
                .findFirst()
                .ifPresent(member -> {
                    throw new HostAlreadyExistException(meetingId, member.getUserId());
                });

        MeetingMember hostMember = MeetingMember.createHostMember(meetingId, userId);
        meetingMemberRepository.save(hostMember);
    }

    public void removeMember(Long meetingId, Long userId) {
        List<MeetingMember> memberList = meetingMemberRepository.findMemberListByMeetingId(meetingId);

        MeetingMember memberToRemove = memberList.stream()
                .filter(member -> member.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(NotMemberException::new);

        meetingMemberRepository.remove(memberToRemove);
        memberList.remove(memberToRemove);

        if (!memberList.isEmpty() && memberToRemove.isHost()) {
            memberList.get(0).updateToHost();
        }
    }
}
