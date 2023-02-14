package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.date.command.application.confirm.DateConfirmFacade;
import com.comeon.backend.date.command.application.voting.VotingFacade;
import com.comeon.backend.meeting.command.domain.MeetingCalendarModifyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingCalendarModifyEventHandler {

    private final VotingFacade votingFacade;
    private final DateConfirmFacade dateConfirmFacade;

    @Async
    @TransactionalEventListener(
            classes = MeetingCalendarModifyEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingCalendarModifyEvent event) {
        Long meetingId = event.getMeetingId();

        // 변경된 캘린더 범위 밖의 투표 내역 삭제
        votingFacade.readjustVotingRange(meetingId, event.getCalendarStartFrom(), event.getCalendarEndTo());

        // 확정된 모임일 삭제
        dateConfirmFacade.removeMeetingConfirmedDateIfExist(meetingId);
    }
}
