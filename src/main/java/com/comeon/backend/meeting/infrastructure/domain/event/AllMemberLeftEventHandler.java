package com.comeon.backend.meeting.infrastructure.domain.event;

import com.comeon.backend.meeting.command.domain.event.AllMemberLeftEvent;
import com.comeon.backend.meeting.infrastructure.domain.repository.MeetingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class AllMemberLeftEventHandler {

    private final MeetingJpaRepository meetingJpaRepository;

    @Async
    @TransactionalEventListener(
            classes = AllMemberLeftEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(AllMemberLeftEvent event) {
        meetingJpaRepository.deleteById(event.getMeetingId());
    }
}
