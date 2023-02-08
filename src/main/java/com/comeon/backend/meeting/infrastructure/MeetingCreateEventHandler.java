package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.meeting.command.domain.MeetingCreateEvent;
import com.comeon.backend.meetingmember.command.application.MeetingMemberFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingCreateEventHandler {

    private final MeetingMemberFacade meetingMemberFacade;

    @TransactionalEventListener(
            classes = MeetingCreateEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT
    )
    public void handle(MeetingCreateEvent event) {
        log.debug("Event: meeting create at meetingId: {}", event.getMeetingId());
        meetingMemberFacade.createHostUser(event.getHostUserId(), event.getMeetingId());
        log.debug("HOST member created!!");
    }
}
