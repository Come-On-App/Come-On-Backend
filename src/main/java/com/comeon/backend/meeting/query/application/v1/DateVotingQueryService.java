package com.comeon.backend.meeting.query.application.v1;

import com.comeon.backend.meeting.query.dto.VotingMemberSimple;
import com.comeon.backend.meeting.query.dao.DateVotingDao;
import com.comeon.backend.meeting.query.dto.DateVotingDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateVotingQueryService {

    @Value("${user.default-image}")
    private String defaultImageUrl;

    private final DateVotingDao dateVotingDao;

    public DateVotingDetails votingDetails(Long meetingId, LocalDate date, Long userId) {
        List<VotingMemberSimple> memberSimpleList = dateVotingDao.findVotingMemberSimpleList(meetingId, date);
        memberSimpleList.stream()
                .filter(votingMemberSimple -> votingMemberSimple.getProfileImageUrl() == null)
                .forEach(votingMemberSimple -> votingMemberSimple.setProfileImageUrl(defaultImageUrl));
        boolean myVoting = memberSimpleList.stream()
                .anyMatch(memberSimple -> memberSimple.getUserId().equals(userId));
        return new DateVotingDetails(date, myVoting, memberSimpleList);
    }
}
