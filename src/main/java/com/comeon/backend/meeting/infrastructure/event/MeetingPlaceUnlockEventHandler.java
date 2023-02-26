package com.comeon.backend.meeting.infrastructure.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MeetingPlaceUnlockEvent;
import com.comeon.backend.meeting.infrastructure.event.message.MeetingPlaceUnlockMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingPlaceUnlockEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingPlaceUnlockEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingPlaceUnlockEvent event) {
        String topic = topics.getMeetingPlaceUnlock();
        MeetingPlaceUnlockMessage message =
                new MeetingPlaceUnlockMessage(event.getMeetingId(), event.getMeetingPlaceId(), event.getUserId());

        producer.produce(topic, message);
    }
}
